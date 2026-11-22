package nest.com.budget_app.controllers.dtos.responses;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import nest.com.budget_app.enums.SavingGoalStatus;

public record SavingsGoalResponse(
    UUID id,
    String name,
    long targetAmount,
    long currentAmount,
    long remainingAmount,
    double percentComplete,
    LocalDate targetDate,
    Long monthlyNeeded,
    Boolean onTrack,
    SavingGoalStatus status,
    String icon,
    String color,
    Instant createdAt,
    Instant updatedAt) {}
