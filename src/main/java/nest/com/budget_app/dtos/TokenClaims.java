package nest.com.budget_app.dtos;

import java.time.Instant;

public record TokenClaims(String sub, String type, Instant iat, Instant exp) {}
