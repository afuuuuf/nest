package nest.com.budget_app.controllers.dtos.responses;

public record SavingsContributionResultResponse(
    SavingsContributionResponse contribution, SavingsGoalResponse goal) {}
