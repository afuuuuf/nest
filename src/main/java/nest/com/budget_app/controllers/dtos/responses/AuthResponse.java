package nest.com.budget_app.controllers.dtos.responses;

public record AuthResponse(String accessToken, String refreshToken, String tokenType) {}
