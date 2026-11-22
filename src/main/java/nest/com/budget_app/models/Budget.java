package nest.com.budget_app.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nest.com.budget_app.enums.Category;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(
    name = "budgets",
    indexes = {@Index(name = "idx_budget_category", columnList = "category")})
@Getter
@Setter
@NoArgsConstructor
public class Budget extends BaseEntity {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_budget_user"))
  private User user;

  @Column(nullable = false)
  private Long amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private Category category;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Month createdMonth;

  private LocalDateTime deletedAt;
}
