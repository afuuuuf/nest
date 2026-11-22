package nest.com.budget_app.dtos;

import nest.com.budget_app.enums.Category;

public record CategoryTotal(Category category, long total) {}
