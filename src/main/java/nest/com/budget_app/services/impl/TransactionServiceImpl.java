package nest.com.budget_app.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import nest.com.budget_app.dtos.CategoryTotal;
import nest.com.budget_app.dtos.TransactionDto;
import nest.com.budget_app.enums.Category;
import nest.com.budget_app.enums.TransactionFlow;
import nest.com.budget_app.exceptions.NotFoundException;
import nest.com.budget_app.mappers.TransactionMapper;
import nest.com.budget_app.models.Transaction;
import nest.com.budget_app.models.User;
import nest.com.budget_app.repositories.TransactionRepository;
import nest.com.budget_app.services.TransactionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private static final String TRANSACTION_NOT_FOUND_MESSAGE = "Transaction %s id for %s not found.";

  private final TransactionMapper transactionMapper;
  private final TransactionRepository transactionRepository;

  @Override
  public TransactionDto createTransaction(TransactionDto transactionDto) {
    Transaction transaction = transactionMapper.toEntity(transactionDto);
    Transaction createdTransaction = transactionRepository.save(transaction);
    return transactionMapper.toDto(createdTransaction);
  }

  @Override
  public TransactionDto getTransactionDetails(UUID id, User user) {
    Transaction transaction = getTransactionByIdAndUserId(id, user);
    return transactionMapper.toDto(transaction);
  }

  @Override
  public List<TransactionDto> getTransactions(UUID userId) {
    List<Transaction> transactionList = transactionRepository.findAllByUser_IdAndDeletedAtIsNull(userId);
    return transactionMapper.toDtos(transactionList);
  }

  @Override
  public void deleteTransaction(UUID id, User user) {
    Transaction transaction = getTransactionByIdAndUserId(id, user);
    transaction.setDeletedAt(LocalDateTime.now());
    transactionRepository.save(transaction);
  }

  @Override
  public long getTransactionSumByTransactionFlowType(TransactionFlow transactionFlow, UUID userId) {
    return transactionRepository.sumByFlow(transactionFlow, userId);
  }

  @Override
  public List<CategoryTotal> getTransactionSumByCategory(Category category, UUID userId) {
    return transactionRepository.sumExpensesByCategory(userId);
  }

  @Override
  public long getNetCashFlow(long totalIncome, long totalExpense) {
    return totalIncome - totalExpense;
  }

  @Override
  public double getNetSavingsRate(long totalIncome, long totalExpense) {
    if (totalIncome == 0) {
      return 0.0;
    }
    long netCashFlow = getNetCashFlow(totalIncome, totalExpense);
    return netCashFlow * 100.0 / totalIncome;
  }

  private Transaction getTransactionByIdAndUserId(UUID id, User user) {
    return transactionRepository
        .findByIdAndUser_IdAndDeletedAtIsNull(id, user.getId())
        .orElseThrow(() ->
            new NotFoundException(TRANSACTION_NOT_FOUND_MESSAGE.formatted(id, user.getUsername())));
  }
}
