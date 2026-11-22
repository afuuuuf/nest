package nest.com.budget_app.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nest.com.budget_app.models.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, UUID> {

  List<SavingsGoal> findByUser_IdAndDeletedAtIsNullOrderByCreatedAtDesc(UUID userId);

  Optional<SavingsGoal> findByIdAndUser_IdAndDeletedAtIsNull(UUID id, UUID userId);
}
