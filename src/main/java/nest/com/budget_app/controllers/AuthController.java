package nest.com.budget_app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nest.com.budget_app.controllers.dtos.requests.AuthLoginRequest;
import nest.com.budget_app.controllers.dtos.requests.AuthRefreshRequest;
import nest.com.budget_app.controllers.dtos.requests.AuthRegisterRequest;
import nest.com.budget_app.controllers.dtos.responses.AuthResponse;
import nest.com.budget_app.dtos.AuthDto;
import nest.com.budget_app.mappers.AuthMapper;
import nest.com.budget_app.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final AuthMapper authMapper;

  @PostMapping("/register")
  public AuthResponse registerUser(@Valid @RequestBody AuthRegisterRequest request) {
    AuthDto authDto = authMapper.toDto(request);
    return authService.register(authDto);
  }

  @PostMapping("/login")
  public AuthResponse login(@Valid @RequestBody AuthLoginRequest request) {
    AuthDto authDto = authMapper.toDto(request);
    return authService.login(authDto);
  }

  @PostMapping("/refresh")
  public AuthResponse refresh(@Valid @RequestBody AuthRefreshRequest request) {
    AuthDto authDto = authMapper.toDto(request);
    return authService.refresh(authDto);
  }
}
