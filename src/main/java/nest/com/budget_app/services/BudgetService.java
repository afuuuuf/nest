package nest.com.budget_app.services;

import java.time.Month;
import java.util.List;
import java.util.UUID;
import nest.com.budget_app.dtos.BudgetDto;
import nest.com.budget_app.enums.Category;
import nest.com.budget_app.models.User;

public interface BudgetService {
  BudgetDto createBudget(BudgetDto budgetDto, User user);

  BudgetDto editBudget(BudgetDto budgetDto);

  BudgetDto getBudgetDetails(Category category, Month month, UUID userId);

  List<BudgetDto> getBudgetListByMonth(Month month, UUID userId);
}
