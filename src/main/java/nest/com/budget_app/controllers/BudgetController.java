package nest.com.budget_app.controllers;

import jakarta.validation.Valid;
import java.time.Month;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nest.com.budget_app.controllers.dtos.requests.BudgetCreateRequest;
import nest.com.budget_app.controllers.dtos.requests.BudgetUpsertRequest;
import nest.com.budget_app.controllers.dtos.responses.BaseBudgetResponse;
import nest.com.budget_app.controllers.dtos.responses.BudgetUpsertResponse;
import nest.com.budget_app.enums.Category;
import nest.com.budget_app.mappers.BudgetMapper;
import nest.com.budget_app.models.User;
import nest.com.budget_app.services.BudgetService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

  private final BudgetService budgetService;
  private final BudgetMapper budgetMapper;

  @PostMapping
  public BudgetUpsertResponse createBudget(
      @AuthenticationPrincipal User user, @Valid @RequestBody BudgetCreateRequest request) {
    return Optional.of(request)
        .map(budgetMapper::toDto)
        .map(dto -> budgetService.createBudget(dto, user))
        .map(budgetMapper::toResp)
        .orElseThrow();
  }

  @PutMapping
  public BudgetUpsertResponse editBudget(
      @AuthenticationPrincipal User user,
      @RequestParam Category category,
      @RequestParam Month month,
      @Valid @RequestBody BudgetUpsertRequest request) {
    return Optional.of(request)
        .map(r -> budgetMapper.toDto(r, user, category, month))
        .map(budgetService::editBudget)
        .map(budgetMapper::toResp)
        .orElseThrow();
  }

  @GetMapping
  public BaseBudgetResponse getBudgetDetails(
      @AuthenticationPrincipal User user,
      @RequestParam Category category,
      @RequestParam Month month) {
    return Optional.of(budgetService.getBudgetDetails(category, month, user.getId()))
        .map(budgetMapper::toResp)
        .orElseThrow();
  }
}
