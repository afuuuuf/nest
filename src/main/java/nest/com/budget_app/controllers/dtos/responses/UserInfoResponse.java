package nest.com.budget_app.controllers.dtos.responses;

import java.util.UUID;

public record UserInfoResponse(
    UUID id, String email, String username, String firstName, String lastName) {}
