package nest.com.budget_app.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nest.com.budget_app.dtos.CategoryTotal;
import nest.com.budget_app.enums.TransactionFlow;
import nest.com.budget_app.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
  List<Transaction> findAllByUser_IdAndDeletedAtIsNull(UUID userId);

  Optional<Transaction> findByIdAndUser_IdAndDeletedAtIsNull(UUID id, UUID userId);

  @Query(
      """
      SELECT COALESCE(SUM(t.amount), 0L)
      FROM Transaction t
      WHERE t.transactionFlow = :flow
        AND t.deletedAt IS NULL
        AND t.user.id = :userId
      """)
  long sumByFlow(@Param("flow") TransactionFlow flow, @Param("userId") UUID userId);

  @Query(
      """
      SELECT new nest.com.budget_app.dtos.CategoryTotal(t.category, SUM(t.amount))
      FROM Transaction t
      WHERE t.transactionFlow = :flow
        AND t.deletedAt IS NULL
        AND t.user.id = :userId
      GROUP BY t.category
      """)
  List<CategoryTotal> sumByCategory(
      @Param("flow") TransactionFlow flow, @Param("userId") UUID userId);

  default List<CategoryTotal> sumExpensesByCategory(UUID userId) {
    return sumByCategory(TransactionFlow.EXPENSE, userId);
  }
}
