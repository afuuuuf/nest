package nest.com.budget_app.services.impl;

import java.time.Month;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import nest.com.budget_app.dtos.BudgetDto;
import nest.com.budget_app.enums.Category;
import nest.com.budget_app.exceptions.NotFoundException;
import nest.com.budget_app.exceptions.UnsupportedActionException;
import nest.com.budget_app.mappers.BudgetMapper;
import nest.com.budget_app.models.Budget;
import nest.com.budget_app.models.User;
import nest.com.budget_app.repositories.BudgetRepository;
import nest.com.budget_app.services.BudgetService;
import nest.com.budget_app.utils.DateUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

  private static final String DUPLICATED_BUDGET_ERROR_MSG =
      "Budget for category %s in %s already exists.";
  private static final String BUDGET_NOT_FOUND_MSG = "Budget for category %s in %s not found.";

  private final BudgetRepository budgetRepository;
  private final BudgetMapper budgetMapper;

  @Override
  public BudgetDto createBudget(BudgetDto budgetDto, User user) {

    Month month = DateUtil.getCurrentMonth();

    if (budgetDto.getCategory() != null
        && budgetRepository.existsByCategoryAndCreatedMonthAndUser_IdAndDeletedAtIsNull(
            budgetDto.getCategory(), month, user.getId())) {

      throw new UnsupportedActionException(
          DUPLICATED_BUDGET_ERROR_MSG.formatted(budgetDto.getCategory(), month));
    }

    Budget budget = budgetMapper.toEntity(budgetDto, month);
    budget.setUser(user);

    Budget savedBudget = budgetRepository.save(budget);

    return budgetMapper.toDto(savedBudget);
  }

  @Override
  public BudgetDto editBudget(BudgetDto budgetDto) {
    Budget budget =
        getBudgetEntityByCategoryAndMonthAndUserId(
            budgetDto.getCategory(), budgetDto.getCreatedMonth(), budgetDto.getUser().getId());
    budget.setAmount(budgetDto.getAmount());
    Budget savedBudget = budgetRepository.save(budget);
    return budgetMapper.toDto(savedBudget);
  }

  @Override
  public BudgetDto getBudgetDetails(Category category, Month month, UUID userId) {
    Budget budget = getBudgetEntityByCategoryAndMonthAndUserId(category, month, userId);
    return budgetMapper.toDto(budget);
  }

  @Override
  public List<BudgetDto> getBudgetListByMonth(Month month, UUID userId) {
    return budgetMapper.toDtos(budgetRepository.findByCreatedMonthAndUser_Id(month, userId));
  }

  private Budget getBudgetEntityByCategoryAndMonthAndUserId(
      Category category, Month month, UUID userId) {
    return budgetRepository
        .findByCategoryAndCreatedMonthAndUser_Id(category, month, userId)
        .orElseThrow(() -> new NotFoundException(BUDGET_NOT_FOUND_MSG.formatted(category, month)));
  }
}
