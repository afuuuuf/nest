package nest.com.budget_app.services;

import java.util.List;
import java.util.UUID;
import nest.com.budget_app.dtos.ContributionResult;
import nest.com.budget_app.dtos.SavingsContributionDto;
import nest.com.budget_app.dtos.SavingsGoalsDto;
import nest.com.budget_app.models.User;

public interface SavingsService {

  SavingsGoalsDto createSavingsGoals(SavingsGoalsDto dto);

  SavingsGoalsDto updateSavingsGoals(UUID goalId, UUID userId, SavingsGoalsDto changes);

  SavingsGoalsDto getSavingsGoals(UUID goalId, UUID userId);

  List<SavingsGoalsDto> listSavingsGoals(UUID userId);

  void deleteSavingsGoals(UUID goalId, UUID userId);

  ContributionResult createContribution(UUID goalId, User user, SavingsContributionDto dto);

  ContributionResult updateContribution(
      UUID goalId, UUID contributionId, UUID userId, SavingsContributionDto changes);

  SavingsGoalsDto deleteContribution(UUID goalId, UUID contributionId, UUID userId);

  List<SavingsContributionDto> listContributions(UUID goalId, UUID userId);
}
