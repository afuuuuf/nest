package nest.com.budget_app.utils;

import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UuidUtil {
  public String uuidV7() {
    return UUID.ofEpochMillis(System.currentTimeMillis()).toString();
  }
}
