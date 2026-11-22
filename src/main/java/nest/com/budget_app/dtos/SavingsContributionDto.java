package nest.com.budget_app.dtos;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingsContributionDto {
  private UUID id;
  private UUID goalId;
  private Long amount;
  private LocalDate contributedAt;
  private String contributedBy;
  private String note;
  private Instant createdAt;
}
