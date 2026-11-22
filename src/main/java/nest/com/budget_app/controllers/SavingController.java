package nest.com.budget_app.controllers;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import nest.com.budget_app.controllers.dtos.requests.SavingsGoalEditRequest;
import nest.com.budget_app.controllers.dtos.requests.SavingsGoalUpsertRequest;
import nest.com.budget_app.controllers.dtos.responses.SavingsContributionListResponse;
import nest.com.budget_app.controllers.dtos.responses.SavingsContributionResultResponse;
import nest.com.budget_app.controllers.dtos.responses.SavingsGoalListResponse;
import nest.com.budget_app.controllers.dtos.responses.SavingsGoalResponse;
import nest.com.budget_app.mappers.SavingsContributionMapper;
import nest.com.budget_app.mappers.SavingsGoalsMapper;
import nest.com.budget_app.models.User;
import nest.com.budget_app.services.SavingsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/savings")
@RequiredArgsConstructor
public class SavingController {

  private final SavingsService savingsService;
  private final SavingsGoalsMapper savingsGoalsMapper;
  private final SavingsContributionMapper savingsContributionMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SavingsGoalResponse createSavingsGoals(
      @AuthenticationPrincipal User user, @Valid @RequestBody SavingsGoalUpsertRequest request) {
    return Optional.of(request)
        .map(r -> savingsGoalsMapper.toDto(r, user))
        .map(dto -> savingsService.createSavingsGoals(dto))
        .map(savingsGoalsMapper::toResp)
        .orElseThrow();
  }

  @PutMapping("/{id}")
  public SavingsGoalResponse updateSavingsGoals(
      @AuthenticationPrincipal User user,
      @PathVariable UUID id,
      @Valid @RequestBody SavingsGoalEditRequest request) {
    return Optional.of(request)
        .map(r -> savingsGoalsMapper.toDto(r))
        .map(changes -> savingsService.updateSavingsGoals(id, user.getId(), changes))
        .map(savingsGoalsMapper::toResp)
        .orElseThrow();
  }

  @GetMapping("/{id}")
  public SavingsGoalResponse getSavingsGoals(
      @AuthenticationPrincipal User user, @PathVariable UUID id) {
    return Optional.of(user)
        .map(User::getId)
        .map(userId -> savingsService.getSavingsGoals(id, userId))
        .map(savingsGoalsMapper::toResp)
        .orElseThrow();
  }

  @GetMapping
  public SavingsGoalListResponse listSavingsGoals(@AuthenticationPrincipal User user) {
    return Optional.of(user)
        .map(User::getId)
        .map(savingsService::listSavingsGoals)
        .map(savingsGoalsMapper::toListResp)
        .orElseThrow();
  }

  @DeleteMapping("/{id}")
  public void deleteSavingsGoals(@AuthenticationPrincipal User user, @PathVariable UUID id) {
    savingsService.deleteSavingsGoals(id, user.getId());
  }

  @PostMapping("/{id}/contributions")
  @ResponseStatus(HttpStatus.CREATED)
  public SavingsContributionResultResponse createContribution(
      @AuthenticationPrincipal User user,
      @PathVariable UUID id,
      @Valid @RequestBody SavingsGoalUpsertRequest request) {
    return Optional.of(request)
        .map(r -> savingsContributionMapper.toDto(r))
        .map(dto -> savingsService.createContribution(id, user, dto))
        .map(savingsContributionMapper::toResultResp)
        .orElseThrow();
  }

  @GetMapping("/{id}/contributions")
  public SavingsContributionListResponse listContributions(
      @AuthenticationPrincipal User user, @PathVariable UUID id) {
    return Optional.of(user)
        .map(User::getId)
        .map(userId -> savingsService.listContributions(id, userId))
        .map(savingsContributionMapper::toListResp)
        .orElseThrow();
  }

  @PutMapping("/{id}/contributions/{contributionId}")
  public SavingsContributionResultResponse updateContribution(
      @AuthenticationPrincipal User user,
      @PathVariable UUID id,
      @PathVariable UUID contributionId,
      @Valid @RequestBody SavingsGoalEditRequest request) {
    return Optional.of(request)
        .map(r -> savingsContributionMapper.toDto(r))
        .map(
            changes -> savingsService.updateContribution(id, contributionId, user.getId(), changes))
        .map(savingsContributionMapper::toResultResp)
        .orElseThrow();
  }

  @DeleteMapping("/{id}/contributions/{contributionId}")
  public SavingsGoalResponse deleteContribution(
      @AuthenticationPrincipal User user,
      @PathVariable UUID id,
      @PathVariable UUID contributionId) {
    return Optional.of(user)
        .map(User::getId)
        .map(userId -> savingsService.deleteContribution(id, contributionId, userId))
        .map(savingsGoalsMapper::toResp)
        .orElseThrow();
  }
}
