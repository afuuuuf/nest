package nest.com.budget_app.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record AuthRefreshRequest(@NotBlank String refreshToken) {}
