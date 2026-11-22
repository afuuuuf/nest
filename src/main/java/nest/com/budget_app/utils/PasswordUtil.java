package nest.com.budget_app.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UtilityClass
public class PasswordUtil {
  private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(12);

  public String hashPassword(String plain) {
    return ENCODER.encode(plain);
  }

  public boolean verifyPassword(String plain, String stored) {
    return ENCODER.matches(plain, stored);
  }
}
