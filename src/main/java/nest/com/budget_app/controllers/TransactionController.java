package nest.com.budget_app.controllers;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import nest.com.budget_app.controllers.dtos.requests.TransactionUpsertRequest;
import nest.com.budget_app.controllers.dtos.responses.TransactionUpsertResponse;
import nest.com.budget_app.dtos.TransactionDto;
import nest.com.budget_app.mappers.TransactionMapper;
import nest.com.budget_app.models.User;
import nest.com.budget_app.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;
  private final TransactionMapper transactionMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TransactionUpsertResponse createTransaction(
      @AuthenticationPrincipal User user, @Valid @RequestBody TransactionUpsertRequest request) {
    return Optional.of(request)
        .map(r -> transactionMapper.toDto(r, user))
        .map(transactionService::createTransaction)
        .map(transactionMapper::toResp)
        .orElseThrow();
  }

  @GetMapping("/{id}")
  public TransactionUpsertResponse getTransactionDetails(
      @AuthenticationPrincipal User user, @PathVariable String id) {
    TransactionDto transactionDto =
        transactionService.getTransactionDetails(UUID.fromString(id), user);
    return transactionMapper.toResp(transactionDto);
  }

  @GetMapping
  public List<TransactionUpsertResponse> getTransactions(@AuthenticationPrincipal User user) {
    return Optional.of(user)
        .map(User::getId)
        .map(transactionService::getTransactions)
        .map(transactionMapper::toResps)
        .orElseThrow();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTransaction(@AuthenticationPrincipal User user, @PathVariable String id) {
    transactionService.deleteTransaction(UUID.fromString(id), user);
  }
}
