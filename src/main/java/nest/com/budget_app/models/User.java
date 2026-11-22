package nest.com.budget_app.models;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(
    name = "users",
    uniqueConstraints = @UniqueConstraint(name = "users_email_key", columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
  private UUID id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String passwordHash;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;
}
