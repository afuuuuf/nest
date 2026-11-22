package nest.com.budget_app.controllers.dtos.responses;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record SavingsContributionResponse(
    UUID id,
    UUID goalId,
    long amount,
    LocalDate contributedAt,
    String contributedBy,
    String note,
    Instant createdAt) {}
