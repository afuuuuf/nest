package nest.com.budget_app.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "savings_contributions")
@Getter
@Setter
@NoArgsConstructor
public class SavingsContribution extends BaseEntity {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "goal_id", nullable = false)
  private SavingsGoal goal;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private Long amount;

  @Column(nullable = false)
  private LocalDate contributedAt;

  private String contributedBy;

  private String note;
}
