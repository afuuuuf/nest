package nest.com.budget_app.controllers.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseAuthRequest {

  @NotBlank
  @Email
  @Size(max = 255)
  private String email;

  @NotBlank
  @Size(min = 8, max = 72)
  private String password;
}
