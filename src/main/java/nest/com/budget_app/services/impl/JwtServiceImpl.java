package nest.com.budget_app.services.impl;

import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import nest.com.budget_app.dtos.TokenClaims;
import nest.com.budget_app.enums.TokenType;
import nest.com.budget_app.exceptions.UnauthorizedException;
import nest.com.budget_app.services.JwtService;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  private static final int ACCESS_TOKEN_MINUTES = 30;
  private static final int REFRESH_TOKEN_DAYS = 30;

  private final MacAlgorithm algorithm;
  private final JwtEncoder encoder;
  private final JwtDecoder decoder;

  @Override
  public String createToken(String userId, TokenType tokenType) {
    Duration lifetime =
        tokenType == TokenType.ACCESS
            ? Duration.ofMinutes(ACCESS_TOKEN_MINUTES)
            : Duration.ofDays(REFRESH_TOKEN_DAYS);
    Instant now = Instant.now();

    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .subject(userId)
            .claim("type", tokenType.getValue())
            .issuedAt(now)
            .expiresAt(now.plus(lifetime))
            .build();
    JwsHeader header = JwsHeader.with(algorithm).build();

    return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
  }

  @Override
  public TokenClaims decodeToken(String token) {
    try {
      Jwt jwt = decoder.decode(token);
      return new TokenClaims(
          jwt.getSubject(), jwt.getClaimAsString("type"), jwt.getIssuedAt(), jwt.getExpiresAt());
    } catch (JwtException e) {
      throw new UnauthorizedException("Invalid or expired token.");
    }
  }
}
