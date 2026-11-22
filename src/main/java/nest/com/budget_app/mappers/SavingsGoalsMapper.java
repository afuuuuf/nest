package nest.com.budget_app.mappers;

import java.util.List;
import nest.com.budget_app.controllers.dtos.requests.SavingsGoalEditRequest;
import nest.com.budget_app.controllers.dtos.requests.SavingsGoalUpsertRequest;
import nest.com.budget_app.controllers.dtos.responses.SavingsGoalListResponse;
import nest.com.budget_app.controllers.dtos.responses.SavingsGoalResponse;
import nest.com.budget_app.dtos.SavingsGoalsDto;
import nest.com.budget_app.models.SavingsGoal;
import nest.com.budget_app.models.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SavingsGoalsMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", source = "user")
  SavingsGoalsDto toDto(SavingsGoalUpsertRequest savingsGoalUpsertRequest, User user);

  SavingsGoal toEntity(SavingsGoalsDto savingsGoalsDto);

  SavingsGoalsDto toDto(SavingsGoal savingsGoal);

  SavingsGoalResponse toResp(SavingsGoalsDto savingsGoalsDto);

  SavingsGoalsDto toDto(SavingsGoalEditRequest request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "status", ignore = true)
  void updateEntity(SavingsGoalsDto changes, @MappingTarget SavingsGoal goal);

  default SavingsGoalListResponse toListResp(List<SavingsGoalsDto> dtos) {
    return new SavingsGoalListResponse(dtos.stream().map(this::toResp).toList());
  }
}
