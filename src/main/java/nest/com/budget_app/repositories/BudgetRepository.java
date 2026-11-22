package nest.com.budget_app.repositories;

import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nest.com.budget_app.enums.Category;
import nest.com.budget_app.models.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {
  boolean existsByCategoryAndCreatedMonthAndUser_IdAndDeletedAtIsNull(
      Category category, Month createdMonth, UUID userId);

  Optional<Budget> findByCategoryAndCreatedMonthAndUser_Id(
      Category category, Month createdMonth, UUID userId);

  List<Budget> findByCreatedMonthAndUser_Id(Month month, UUID userId);
}
