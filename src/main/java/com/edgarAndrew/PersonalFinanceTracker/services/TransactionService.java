package com.edgarAndrew.PersonalFinanceTracker.services;

import com.edgarAndrew.PersonalFinanceTracker.DTO.transaction.GetTransactionResponse;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.transaction.GetTransactionByCategoryResponse;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.transaction.InvalidCategoryException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.transaction.TransactionNotFoundException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.user.AuthorizationException;
import com.edgarAndrew.PersonalFinanceTracker.models.BankAccount;
import com.edgarAndrew.PersonalFinanceTracker.models.Transaction;
import com.edgarAndrew.PersonalFinanceTracker.models.user.User;

import java.time.LocalDate;
import java.util.Arrays;
import com.edgarAndrew.PersonalFinanceTracker.repositories.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountService bankAccountService;
    private final BudgetService budgetService;

    public TransactionService(TransactionRepository transactionRepository, BankAccountService bankAccountService,BudgetService budgetService) {
        this.transactionRepository = transactionRepository;
        this.bankAccountService = bankAccountService;
        this.budgetService = budgetService;
    }

    @Transactional
    public Transaction addTransaction(Long bankAccountId, double amount, String type, String category,Boolean includeBudget) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        BankAccount bankAccount = bankAccountService.getBankAccountById(bankAccountId);

        if(!bankAccount.getUser().getId().equals(user.getId()))
            throw new AuthorizationException("Bank Account with id " + bankAccountId + "does not belong to you");

        try {
            Transaction.Category.valueOf(category);
        }catch (IllegalArgumentException e){
            throw new InvalidCategoryException("No category with category name: " + category);
        }

        Transaction transaction = new Transaction(amount,type,Transaction.Category.valueOf(category),LocalDateTime.now(),bankAccount);
        bankAccountService.updateBankAccountBalance(bankAccount, amount, type);

        // if budget field is true & it is an expense then deduct money from the budget for that month
        if(includeBudget && type.equals("EXPENSE")){
            budgetService.deductMoneyFromBudget(user,amount);
        }

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction getTransactionById(Long id){
        return transactionRepository.findById(id).orElseThrow(()->new TransactionNotFoundException("Transaction not found with id: " + id));
    }

    @Transactional
    public void removeTransaction(Long transactionId) {
        Transaction transaction = this.getTransactionById(transactionId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if(!transaction.getBankAccount().getUser().getId().equals(user.getId()))
            throw new AuthorizationException("Transaction with id " + transactionId + "does not belong to you");

        transactionRepository.deleteById(transactionId);
    }

    public List<String> getAllCategories() {
        return Arrays.stream(Transaction.Category.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public List<GetTransactionResponse> getTransactions(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<Transaction> transactions = transactionRepository.findByBankAccountUserAndDateBetween(user, startDateTime, endDateTime);

        return transactions.stream()
                .map(transaction -> new GetTransactionResponse(
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getCategory(),
                        transaction.getDate(),
                        transaction.getBankAccount().getAccountNumber()
                ))
                .collect(Collectors.toList());

    }

    public List<GetTransactionResponse> getTransactions(LocalDate startDate, LocalDate endDate,String type) {
        if(type.equals("BOTH"))
            return this.getTransactions(startDate,endDate);

        if(type.equals("INCOME") || type.equals("EXPENSE")){
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();

            List<Transaction> transactions = transactionRepository.findByBankAccountUserAndDateBetween(user, startDateTime, endDateTime);

            return transactions.stream()
                    .filter(transaction -> type.equalsIgnoreCase(transaction.getType()))
                    .map(transaction -> new GetTransactionResponse(
                            transaction.getId(),
                            transaction.getAmount(),
                            transaction.getType(),
                            transaction.getCategory(),
                            transaction.getDate(),
                            transaction.getBankAccount().getAccountNumber()
                    ))
                    .collect(Collectors.toList());
        }else
            throw new IllegalArgumentException("'type' can only be 'INCOME' or 'EXPENSE'");

    }

    // Get Transactions with paging implementation
    public Page<GetTransactionResponse> getTransactions(
            LocalDate startDate, LocalDate endDate, String type, Pageable pageable) {
        if (type.equals("BOTH")) {
            return getTransactions(startDate, endDate, pageable);
        } else if (type.equals("INCOME") || type.equals("EXPENSE")) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();

            Page<Transaction> transactions = transactionRepository.findTransactionsByUserAndDateBetweenAndType(
                    user, startDateTime, endDateTime, type ,pageable);

            return transactions.map(transaction -> new GetTransactionResponse(
                    transaction.getId(),
                    transaction.getAmount(),
                    transaction.getType(),
                    transaction.getCategory(),
                    transaction.getDate(),
                    transaction.getBankAccount().getAccountNumber()
            ));
        } else {
            throw new IllegalArgumentException("'type' can only be 'INCOME' or 'EXPENSE'");
        }
    }

    private Page<GetTransactionResponse> getTransactions(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Page<Transaction> transactions = transactionRepository.findTransactionsByUserAndDateBetween(
                user, startDateTime, endDateTime, pageable);

        return transactions.map(transaction -> new GetTransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getDate(),
                transaction.getBankAccount().getAccountNumber()
        ));
    }

    public List<GetTransactionByCategoryResponse> getCategoryPercentageAndAmount(LocalDate startDate, LocalDate endDate,String type) {
        List<GetTransactionResponse> transactions = getTransactions(startDate, endDate,type);

        // We use getTransactions without paging because we will be grouping by categories which are finite

        double totalAmount = transactions.stream()
                .mapToDouble(GetTransactionResponse::getAmount)
                .sum();

        Map<Transaction.Category, Double> categoryAmounts = transactions.stream()
                .collect(Collectors.groupingBy(
                        GetTransactionResponse::getCategory,
                        Collectors.summingDouble(GetTransactionResponse::getAmount)
                ));

        return categoryAmounts.entrySet().stream()
                .map(entry -> {
                    String categoryName = entry.getKey().toString();
                    double amount = entry.getValue();
                    double percentage = (amount / totalAmount) * 100.0;

                    String actualType = transactions.stream()
                            .filter(transaction -> transaction.getCategory() == entry.getKey())
                            .findFirst()
                            .map(GetTransactionResponse::getType)
                            .orElse(type);

                    return new GetTransactionByCategoryResponse(categoryName, percentage, amount,actualType);
                })
                .collect(Collectors.toList());
    }


    public List<Transaction> getAllTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return transactionRepository.findByBankAccountUser(user);
    }

    // All transactions with pagination
    public Page<GetTransactionResponse> getAllTransactions(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Page<Transaction> transactionsPage = transactionRepository.findTransactionByBankAccountUser(user, pageable);

        return transactionsPage.map(transaction -> new GetTransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getDate(),
                transaction.getBankAccount().getAccountNumber()
        ));
    }
}
