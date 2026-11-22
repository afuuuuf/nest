package nest.com.budget_app.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDto {
  private String email;
  private String password;
  private String hashedPassword;
  private String username;
  private String firstName;
  private String lastName;
  private String accessToken;
  private String refreshToken;
  private String tokenType;
}
