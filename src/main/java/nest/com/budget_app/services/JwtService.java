package nest.com.budget_app.services;

import nest.com.budget_app.dtos.TokenClaims;
import nest.com.budget_app.enums.TokenType;

public interface JwtService {
  String createToken(String userId, TokenType tokenType);

  TokenClaims decodeToken(String token);
}
