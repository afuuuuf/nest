package nest.com.budget_app.dtos;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nest.com.budget_app.enums.SavingGoalStatus;
import nest.com.budget_app.models.User;

@Getter
@Setter
@NoArgsConstructor
public class SavingsGoalsDto {
  private UUID id;
  private User user;
  private String name;
  private long targetAmount;
  private long currentAmount;
  private long remainingAmount;
  private Double percentComplete;
  private LocalDate targetDate;
  private Long monthlyNeeded;
  private Boolean onTrack;
  private SavingGoalStatus status;
  private String icon;
  private String color;
  private Instant createdAt;
  private Instant updatedAt;
}
