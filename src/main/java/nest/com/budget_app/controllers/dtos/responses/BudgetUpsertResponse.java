package nest.com.budget_app.controllers.dtos.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BudgetUpsertResponse extends BaseBudgetResponse {
  private String id;
}
