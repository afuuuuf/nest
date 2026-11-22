package nest.com.budget_app.mappers;

import java.time.Month;
import java.util.List;
import nest.com.budget_app.controllers.dtos.requests.BudgetCreateRequest;
import nest.com.budget_app.controllers.dtos.requests.BudgetUpsertRequest;
import nest.com.budget_app.controllers.dtos.responses.BudgetUpsertResponse;
import nest.com.budget_app.dtos.BudgetDto;
import nest.com.budget_app.enums.Category;
import nest.com.budget_app.models.Budget;
import nest.com.budget_app.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BudgetMapper {
  BudgetDto toDto(BudgetCreateRequest createRequest);

  @Mapping(target = "category", source = "category")
  @Mapping(target = "createdMonth", source = "month")
  @Mapping(target = "user", source = "user")
  BudgetDto toDto(BudgetUpsertRequest upsertRequest, User user, Category category, Month month);

  @Mapping(target = "createdMonth", source = "month")
  Budget toEntity(BudgetDto budgetDto, Month month);

  BudgetDto toDto(Budget budget);

  List<BudgetDto> toDtos(List<Budget> budgets);

  BudgetUpsertResponse toResp(BudgetDto budgetDto);
}
