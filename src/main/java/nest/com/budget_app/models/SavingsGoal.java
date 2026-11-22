package nest.com.budget_app.models;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nest.com.budget_app.enums.SavingGoalStatus;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "savings_goals")
@Getter
@Setter
@NoArgsConstructor
public class SavingsGoal extends BaseEntity {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false)
  private Long targetAmount;

  private LocalDate targetDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private SavingGoalStatus status = SavingGoalStatus.ACTIVE;

  @Column(length = 50)
  private String icon;

  @Column(length = 7)
  private String color;

  private Instant deletedAt;
}
