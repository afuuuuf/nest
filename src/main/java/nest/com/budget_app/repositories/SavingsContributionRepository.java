package nest.com.budget_app.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nest.com.budget_app.dtos.GoalTotal;
import nest.com.budget_app.models.SavingsContribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SavingsContributionRepository extends JpaRepository<SavingsContribution, UUID> {

  Optional<SavingsContribution> findByIdAndGoal_IdAndDeletedAtIsNull(UUID id, UUID goalId);

  List<SavingsContribution> findByGoal_IdOrderByContributedAtDescCreatedAtDesc(UUID goalId);

  @Query("SELECT COALESCE(SUM(c.amount), 0L) FROM SavingsContribution c WHERE c.goal.id = :goalId")
  long sumAmountByGoalId(@Param("goalId") UUID goalId);

  // One grouped query for all of a user's goals (avoids one SUM query per goal).
  @Query(
      """
      SELECT new nest.com.budget_app.dtos.GoalTotal(c.goal.id, SUM(c.amount))
      FROM SavingsContribution c
      WHERE c.goal.user.id = :userId AND c.goal.deletedAt IS NULL
      GROUP BY c.goal.id
      """)
  List<GoalTotal> sumByGoalForUser(@Param("userId") UUID userId);
}
