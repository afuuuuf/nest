package nest.com.budget_app.controllers.dtos.responses;

import java.time.Instant;
import java.util.UUID;
import nest.com.budget_app.enums.Category;
import nest.com.budget_app.enums.TransactionFlow;

public record TransactionUpsertResponse(
    UUID id,
    String description,
    long amount,
    Category category,
    TransactionFlow transactionFlow,
    Instant createdAt,
    Instant updatedAt) {}
