package nest.com.budget_app.services.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import nest.com.budget_app.controllers.dtos.responses.AuthResponse;
import nest.com.budget_app.dtos.AuthDto;
import nest.com.budget_app.dtos.TokenClaims;
import nest.com.budget_app.enums.TokenType;
import nest.com.budget_app.exceptions.UnauthorizedException;
import nest.com.budget_app.exceptions.UnsupportedActionException;
import nest.com.budget_app.models.User;
import nest.com.budget_app.repositories.UserRepository;
import nest.com.budget_app.services.AuthService;
import nest.com.budget_app.services.JwtService;
import nest.com.budget_app.utils.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final JwtService jwtService;
  private final UserRepository userRepository;

  @Override
  public AuthResponse register(AuthDto authDto) {
    if (userRepository.findByEmail(authDto.getEmail()).isPresent()) {
      throw new UnsupportedActionException("Email already registered.");
    }
    User user = new User();
    user.setEmail(authDto.getEmail());
    user.setPasswordHash(PasswordUtil.hashPassword(authDto.getPassword()));
    user.setUsername(authDto.getUsername());
    user.setFirstName(authDto.getFirstName());
    user.setLastName(authDto.getLastName());

    User savedUser = userRepository.save(user);
    return issueTokens(String.valueOf(savedUser.getId()));
  }

  @Override
  public AuthResponse login(AuthDto authDto) {
    User user =
        userRepository
            .findByEmail(authDto.getEmail())
            .filter(u -> PasswordUtil.verifyPassword(authDto.getPassword(), u.getPasswordHash()))
            .orElseThrow(() -> new UnauthorizedException("Invalid email or password."));
    return issueTokens(String.valueOf(user.getId()));
  }

  @Override
  public AuthResponse refresh(AuthDto authDto) {
    TokenClaims payload = jwtService.decodeToken(authDto.getRefreshToken());
    if (!payload.type().equals(TokenType.REFRESH.getValue())) {
      throw new UnauthorizedException("Refresh token required");
    }
    User user =
        userRepository
            .findById(UUID.fromString(payload.sub()))
            .orElseThrow(() -> new UnauthorizedException("User no longer exists"));
    return issueTokens(String.valueOf(user.getId()));
  }

  private AuthResponse issueTokens(String userId) {
    return new AuthResponse(
        jwtService.createToken(userId, TokenType.ACCESS),
        jwtService.createToken(userId, TokenType.REFRESH),
        TokenType.BEARER.toString().toLowerCase());
  }
}
