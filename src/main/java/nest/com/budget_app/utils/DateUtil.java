package nest.com.budget_app.utils;

import java.time.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtil {
  private static final ZoneId LOCAL_TIMEZONE = ZoneId.of("Asia/Kuala_Lumpur");

  public ZonedDateTime getCurrentTime() {
    return ZonedDateTime.now(LOCAL_TIMEZONE);
  }

  public LocalDate getTodayDate() {
    return getCurrentTime().toLocalDate();
  }

  public boolean isAfterDate(LocalDate exactDate, LocalDate targetDate) {
    return exactDate.isAfter(targetDate);
  }

  public Month getCurrentMonth() {
    return getCurrentTime().getMonth();
  }

  public LocalDate toLocalDate(Instant instant) {
    return instant.atZone(LOCAL_TIMEZONE).toLocalDate();
  }
}
