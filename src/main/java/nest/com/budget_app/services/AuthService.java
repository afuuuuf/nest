package nest.com.budget_app.services;

import nest.com.budget_app.controllers.dtos.responses.AuthResponse;
import nest.com.budget_app.dtos.AuthDto;

public interface AuthService {
  AuthResponse register(AuthDto authDto);

  AuthResponse login(AuthDto authDto);

  AuthResponse refresh(AuthDto authDto);
}
