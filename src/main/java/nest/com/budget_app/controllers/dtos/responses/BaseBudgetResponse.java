package nest.com.budget_app.controllers.dtos.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nest.com.budget_app.enums.Category;

@Getter
@Setter
@NoArgsConstructor
public class BaseBudgetResponse {
  private Category category;
  private Long amount;
}
