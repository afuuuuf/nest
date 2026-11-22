package nest.com.budget_app.services.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nest.com.budget_app.dtos.ContributionResult;
import nest.com.budget_app.dtos.GoalTotal;
import nest.com.budget_app.dtos.SavingsContributionDto;
import nest.com.budget_app.dtos.SavingsGoalsDto;
import nest.com.budget_app.enums.SavingGoalStatus;
import nest.com.budget_app.exceptions.NotFoundException;
import nest.com.budget_app.mappers.SavingsContributionMapper;
import nest.com.budget_app.mappers.SavingsGoalsMapper;
import nest.com.budget_app.models.SavingsContribution;
import nest.com.budget_app.models.SavingsGoal;
import nest.com.budget_app.models.User;
import nest.com.budget_app.repositories.SavingsContributionRepository;
import nest.com.budget_app.repositories.SavingsGoalRepository;
import nest.com.budget_app.services.SavingsService;
import nest.com.budget_app.utils.DateUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingsServiceImpl implements SavingsService {

  private static final String GOAL_NOT_FOUND = "Saving Goals Not Found";
  private static final String CONTRIBUTION_NOT_FOUND = "Saving Contribution Not Found";

  private final SavingsGoalsMapper savingsGoalsMapper;
  private final SavingsContributionMapper savingsContributionMapper;
  private final SavingsGoalRepository savingsGoalRepository;
  private final SavingsContributionRepository savingsContributionRepository;

  @Override
  public SavingsGoalsDto createSavingsGoals(SavingsGoalsDto dto) {
    SavingsGoal goal = savingsGoalsMapper.toEntity(dto);
    goal.setStatus(SavingGoalStatus.ACTIVE);
    SavingsGoal saved = savingsGoalRepository.save(goal);
    return withProgress(savingsGoalsMapper.toDto(saved), 0L);
  }

  @Override
  public SavingsGoalsDto updateSavingsGoals(UUID goalId, UUID userId, SavingsGoalsDto changes) {
    SavingsGoal goal = getSavingsGoalByIdAndUserId(goalId, userId);
    savingsGoalsMapper.updateEntity(changes, goal);
    SavingsGoal saved = savingsGoalRepository.save(goal);
    return refreshGoalProgress(saved);
  }

  @Override
  public SavingsGoalsDto getSavingsGoals(UUID goalId, UUID userId) {
    SavingsGoal goal = getSavingsGoalByIdAndUserId(goalId, userId);
    long current = savingsContributionRepository.sumAmountByGoalId(goal.getId());
    return withProgress(savingsGoalsMapper.toDto(goal), current);
  }

  @Override
  public List<SavingsGoalsDto> listSavingsGoals(UUID userId) {
    List<SavingsGoal> goals =
        savingsGoalRepository.findByUser_IdAndDeletedAtIsNullOrderByCreatedAtDesc(userId);
    Map<UUID, Long> totals =
        savingsContributionRepository.sumByGoalForUser(userId).stream()
            .collect(Collectors.toMap(GoalTotal::goalId, GoalTotal::total));
    return goals.stream()
        .map(g -> withProgress(savingsGoalsMapper.toDto(g), totals.getOrDefault(g.getId(), 0L)))
        .toList();
  }

  @Override
  public void deleteSavingsGoals(UUID goalId, UUID userId) {
    SavingsGoal goal = getSavingsGoalByIdAndUserId(goalId, userId);
    goal.setDeletedAt(Instant.now()); // soft delete
    savingsGoalRepository.save(goal);
  }

  @Override
  public ContributionResult createContribution(UUID goalId, User user, SavingsContributionDto dto) {
    SavingsGoal goal = getSavingsGoalByIdAndUserId(goalId, user.getId());

    SavingsContribution contribution = new SavingsContribution();
    contribution.setGoal(goal);
    contribution.setUser(user);
    contribution.setAmount(dto.getAmount());
    contribution.setNote(dto.getNote());
    contribution.setContributedAt(DateUtil.getTodayDate());
    contribution.setContributedBy(user.getEmail());
    SavingsContribution saved = savingsContributionRepository.save(contribution);

    return new ContributionResult(
        savingsContributionMapper.toDto(saved), refreshGoalProgress(goal));
  }

  @Override
  public ContributionResult updateContribution(
      UUID goalId, UUID contributionId, UUID userId, SavingsContributionDto changes) {
    SavingsGoal goal = getSavingsGoalByIdAndUserId(goalId, userId);
    SavingsContribution contribution = getSavingsContributionByIdAndGoalId(contributionId, goalId);

    savingsContributionMapper.updateEntity(changes, contribution);
    SavingsContribution saved = savingsContributionRepository.save(contribution);

    return new ContributionResult(
        savingsContributionMapper.toDto(saved), refreshGoalProgress(goal));
  }

  @Override
  public SavingsGoalsDto deleteContribution(UUID goalId, UUID contributionId, UUID userId) {
    SavingsGoal goal = getSavingsGoalByIdAndUserId(goalId, userId);
    SavingsContribution contribution = getSavingsContributionByIdAndGoalId(contributionId, goalId);

    savingsContributionRepository.delete(contribution);
    return refreshGoalProgress(goal);
  }

  @Override
  public List<SavingsContributionDto> listContributions(UUID goalId, UUID userId) {
    getSavingsGoalByIdAndUserId(goalId, userId);
    return savingsContributionMapper.toDtos(
        savingsContributionRepository.findByGoal_IdOrderByContributedAtDescCreatedAtDesc(goalId));
  }

  private SavingsGoal getSavingsGoalByIdAndUserId(UUID goalId, UUID userId) {
    return savingsGoalRepository
        .findByIdAndUser_IdAndDeletedAtIsNull(goalId, userId)
        .orElseThrow(() -> new NotFoundException(GOAL_NOT_FOUND));
  }

  private SavingsContribution getSavingsContributionByIdAndGoalId(UUID contributionId, UUID goalId) {
    return savingsContributionRepository
        .findByIdAndGoal_IdAndDeletedAtIsNull(contributionId, goalId)
        .orElseThrow(() -> new NotFoundException(CONTRIBUTION_NOT_FOUND));
  }

  private SavingsGoalsDto refreshGoalProgress(SavingsGoal goal) {
    long current = savingsContributionRepository.sumAmountByGoalId(goal.getId());
    SavingGoalStatus next = goal.getStatus().getStatus(current, goal.getTargetAmount());
    if (next != goal.getStatus()) {
      goal.setStatus(next);
      savingsGoalRepository.save(goal);
    }
    return withProgress(savingsGoalsMapper.toDto(goal), current);
  }

  private SavingsGoalsDto withProgress(SavingsGoalsDto dto, long current) {
    long target = dto.getTargetAmount();
    long remaining = Math.max(target - current, 0L);
    double percent = Math.clamp(current * 100.0 / target, 0.0, 100.0);

    dto.setCurrentAmount(current);
    dto.setRemainingAmount(remaining);
    dto.setPercentComplete(Math.round(percent * 100.0) / 100.0);
    applyPace(dto, remaining, percent);
    return dto;
  }

  private void applyPace(SavingsGoalsDto dto, long remaining, double percent) {
    LocalDate deadline = dto.getTargetDate();
    if (deadline == null) {
      dto.setMonthlyNeeded(null);
      dto.setOnTrack(null);
      return;
    }
    if (remaining == 0) {
      dto.setMonthlyNeeded(0L);
      dto.setOnTrack(true);
      return;
    }

    LocalDate today = DateUtil.getTodayDate();
    if (!DateUtil.isAfterDate(deadline, today)) {
      dto.setMonthlyNeeded(remaining);
      dto.setOnTrack(false);
      return;
    }

    long monthsLeft = Math.max(1L, ChronoUnit.MONTHS.between(today, deadline));
    dto.setMonthlyNeeded(Math.ceilDiv(remaining, monthsLeft));

    Instant created = dto.getCreatedAt();
    LocalDate start = created == null ? today : DateUtil.toLocalDate(created);
    long totalDays = Math.max(1L, ChronoUnit.DAYS.between(start, deadline));
    long elapsedDays = ChronoUnit.DAYS.between(start, today);
    double timeElapsed = Math.clamp((double) elapsedDays / totalDays, 0.0, 1.0);
    dto.setOnTrack(percent / 100.0 >= timeElapsed);
  }
}
