package nest.com.budget_app.mappers;

import java.util.List;
import nest.com.budget_app.controllers.dtos.requests.TransactionUpsertRequest;
import nest.com.budget_app.controllers.dtos.responses.TransactionUpsertResponse;
import nest.com.budget_app.dtos.TransactionDto;
import nest.com.budget_app.models.Transaction;
import nest.com.budget_app.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", source = "user")
  TransactionDto toDto(TransactionUpsertRequest request, User user);

  TransactionDto toDto(Transaction transaction);

  List<TransactionDto> toDtos(List<Transaction> transactions);

  Transaction toEntity(TransactionDto transactionDto);

  TransactionUpsertResponse toResp(TransactionDto transactionDto);

  List<TransactionUpsertResponse> toResps(List<TransactionDto> dtos);
}
