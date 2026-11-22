package nest.com.budget_app.services;

import java.util.List;
import java.util.UUID;
import nest.com.budget_app.dtos.CategoryTotal;
import nest.com.budget_app.dtos.TransactionDto;
import nest.com.budget_app.enums.Category;
import nest.com.budget_app.enums.TransactionFlow;
import nest.com.budget_app.models.User;

public interface TransactionService {
  TransactionDto createTransaction(TransactionDto transactionDto);

  TransactionDto getTransactionDetails(UUID id, User user);

  List<TransactionDto> getTransactions(UUID userId);

  void deleteTransaction(UUID id, User user);

  long getTransactionSumByTransactionFlowType(TransactionFlow transactionFlow, UUID userId);

  List<CategoryTotal> getTransactionSumByCategory(Category category, UUID userId);

  long getNetCashFlow(long totalIncome, long totalExpense);

  double getNetSavingsRate(long totalIncome, long totalExpense);
}
