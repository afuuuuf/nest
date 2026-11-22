package nest.com.budget_app.dtos;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nest.com.budget_app.enums.Category;
import nest.com.budget_app.enums.TransactionFlow;
import nest.com.budget_app.models.User;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {
  private UUID id;
  private User user;
  private String description;
  private long amount;
  private Category category;
  private TransactionFlow transactionFlow;
  private Instant createdAt;
  private Instant updatedAt;
}
