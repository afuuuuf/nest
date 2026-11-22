package nest.com.budget_app.controllers.dtos.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nest.com.budget_app.enums.SavingGoalStatus;

@Getter
@Setter
@NoArgsConstructor
public class SavingsGoalEditRequest extends SavingsGoalUpsertRequest {

  private SavingGoalStatus status;
}
