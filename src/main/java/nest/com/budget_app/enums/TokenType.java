package nest.com.budget_app.enums;

import lombok.Getter;

@Getter
public enum TokenType {
  ACCESS("access"),
  REFRESH("refresh"),
  BEARER("bearer");

  private final String value;

  TokenType(String value) {
    this.value = value;
  }
}
