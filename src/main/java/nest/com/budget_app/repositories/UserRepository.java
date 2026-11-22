package nest.com.budget_app.repositories;

import java.util.Optional;
import java.util.UUID;
import nest.com.budget_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);
}
