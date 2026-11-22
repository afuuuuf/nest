package nest.com.budget_app.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nest.com.budget_app.enums.Category;
import nest.com.budget_app.enums.TransactionFlow;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(
    name = "transactions",
    indexes = {@Index(name = "idx_transaction_category", columnList = "category")})
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends BaseEntity {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "user_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_transaction_user"))
  private User user;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Long amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private Category category;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TransactionFlow transactionFlow;

  private LocalDateTime deletedAt;
}
