package nest.com.budget_app.enums;

public enum SavingGoalStatus {
  ACTIVE,
  ON_HOLD,
  COMPLETED;

  public SavingGoalStatus getStatus(long current, long targetAmount) {
    if (current >= targetAmount) {
      return COMPLETED;
    } else if (COMPLETED == this) {
      return ACTIVE;
    }
    return this;
  }
}
