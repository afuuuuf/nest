package nest.com.budget_app.controllers.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nest.com.budget_app.enums.Category;

@Getter
@Setter
@NoArgsConstructor
public class BudgetCreateRequest extends BudgetUpsertRequest {
  @NotNull(message = "Category must be not null")
  private Category category;
}
