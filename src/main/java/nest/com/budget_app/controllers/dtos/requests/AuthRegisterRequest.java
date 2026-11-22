package nest.com.budget_app.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthRegisterRequest extends BaseAuthRequest {

  @NotBlank
  @Size(min = 3, max = 50)
  private String username;

  @NotBlank
  @Size(max = 100)
  private String firstName;

  @NotBlank
  @Size(max = 100)
  private String lastName;
}
