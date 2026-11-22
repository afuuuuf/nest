package nest.com.budget_app.controllers.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BudgetUpsertRequest {
  @NotNull(message = "Amount must be not null")
  @Positive
  private Long amount;
}
