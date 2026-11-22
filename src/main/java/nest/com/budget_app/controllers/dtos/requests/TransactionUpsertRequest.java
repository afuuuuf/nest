package nest.com.budget_app.controllers.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nest.com.budget_app.enums.Category;
import nest.com.budget_app.enums.TransactionFlow;

@Getter
@Setter
@NoArgsConstructor
public class TransactionUpsertRequest {

  private String description;

  @NotNull @Positive private long amount;

  private Category category;

  private TransactionFlow transactionFlow;
}
