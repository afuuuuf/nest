package nest.com.budget_app.controllers.dtos.requests;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SavingsGoalUpsertRequest {

  private static final String COLOR_REGEX = "^#[0-9a-fA-F]{6}$";

  @NotBlank(message = "Name should not be null")
  @Size(max = 100)
  private String name;

  @NotNull @Positive private Long targetAmount;

  @Future(message = "Target date must be in the future")
  private LocalDate targetDate;

  @Size(max = 50)
  private String icon;

  @Pattern(regexp = COLOR_REGEX, message = "Color must be a hex value like #1A2B3C")
  private String color;
}
