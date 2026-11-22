package nest.com.budget_app.dtos;

import java.time.Month;
import lombok.Getter;
import lombok.Setter;
import nest.com.budget_app.enums.Category;
import nest.com.budget_app.models.User;

@Getter
@Setter
public class BudgetDto {
  private String id;
  private User user;
  private Category category;
  private Long amount;
  private Month createdMonth;
}
