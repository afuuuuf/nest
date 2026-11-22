package nest.com.budget_app.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Status {
  CREATED("Created Successfully"),
  DELETED("Deleted Successfully"),
  UPDATED("Updated Successfully"),
  RESTORED("Restored Successfully");

  @JsonValue private final String message;

  Status(String message) {
    this.message = message;
  }
}
