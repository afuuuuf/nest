package nest.com.budget_app.mappers;

import java.util.List;
import nest.com.budget_app.controllers.dtos.requests.SavingsGoalEditRequest;
import nest.com.budget_app.controllers.dtos.requests.SavingsGoalUpsertRequest;
import nest.com.budget_app.controllers.dtos.responses.SavingsContributionListResponse;
import nest.com.budget_app.controllers.dtos.responses.SavingsContributionResponse;
import nest.com.budget_app.controllers.dtos.responses.SavingsContributionResultResponse;
import nest.com.budget_app.dtos.ContributionResult;
import nest.com.budget_app.dtos.SavingsContributionDto;
import nest.com.budget_app.models.SavingsContribution;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    uses = SavingsGoalsMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SavingsContributionMapper {

  @Mapping(source = "goal.id", target = "goalId")
  SavingsContributionDto toDto(SavingsContribution entity);

  List<SavingsContributionDto> toDtos(List<SavingsContribution> entities);

  SavingsContributionDto toDto(SavingsGoalUpsertRequest request);

  SavingsContributionDto toDto(SavingsGoalEditRequest request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "goal", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "contributedBy", ignore = true)
  void updateEntity(SavingsContributionDto changes, @MappingTarget SavingsContribution entity);

  SavingsContributionResponse toResp(SavingsContributionDto dto);

  SavingsContributionResultResponse toResultResp(ContributionResult result);

  default SavingsContributionListResponse toListResp(List<SavingsContributionDto> dtos) {
    return new SavingsContributionListResponse(dtos.stream().map(this::toResp).toList());
  }
}
