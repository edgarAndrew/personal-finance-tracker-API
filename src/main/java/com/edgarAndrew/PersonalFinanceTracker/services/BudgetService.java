package com.edgarAndrew.PersonalFinanceTracker.services;

import com.edgarAndrew.PersonalFinanceTracker.DTO.bank.GetBankAccountsResponse;
import com.edgarAndrew.PersonalFinanceTracker.DTO.budget.GetBudgetsResponse;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.budget.BudgetNotFoundException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.budget.DuplicateBudgetException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.user.AuthorizationException;
import com.edgarAndrew.PersonalFinanceTracker.models.BankAccount;
import com.edgarAndrew.PersonalFinanceTracker.models.Budget;
import com.edgarAndrew.PersonalFinanceTracker.repositories.BudgetRepository;
import com.edgarAndrew.PersonalFinanceTracker.DTO.budget.UpdateBudgetRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.edgarAndrew.PersonalFinanceTracker.models.user.User;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.edgarAndrew.PersonalFinanceTracker.exceptions.bank.InsufficientFundsException;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Transactional
    public Budget addBudget(String name, double startAmount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        YearMonth currentMonth = YearMonth.now();
        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();

        // One month can only have one budget
        if (budgetRepository.existsBudgetForDateRange(user, startDate, endDate)) {
            throw new DuplicateBudgetException("Budget already exists for the current month");
        }

        Budget budget = new Budget(name, startAmount, startDate, endDate, user);
        return budgetRepository.save(budget);
    }

    @Transactional
    public Budget getBudgetById(Long id){
        return budgetRepository.findById(id).orElseThrow(()-> new BudgetNotFoundException("Budget not found with id : "+id));
    }

    @Transactional
    public void removeBudget(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Budget budget = this.getBudgetById(id);

        if(!budget.getUser().getId().equals(user.getId()))
            throw new AuthorizationException("Budget with id "+ id +" does not belong to you");

        budgetRepository.deleteById(id);
    }

    @Transactional
    public void deductMoneyFromBudget(User user, double amount) {

        YearMonth currentMonth = YearMonth.now();
        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();

        // Find the budget for the specified month
        if (!budgetRepository.existsBudgetForDateRange(user, startDate, endDate)) {
            throw new BudgetNotFoundException("No budget found for the current month");
        }

        Optional<Budget> optionalBudget = budgetRepository.findBudgetForCurrentMonth(user,LocalDate.now());

        Budget budget = optionalBudget.get();

        // Deduct money from the budget
        double remainingAmount = budget.getCurrentAmount() - amount;

        // Ensure the remaining budget is not negative
        if (remainingAmount < 0) {
            throw new InsufficientFundsException("Transaction exceeding monthly budget, update budget or don't include transaction in monthly budget");
        }

        budget.setCurrentAmount(remainingAmount);
        budgetRepository.save(budget);
    }

    @Transactional
    public void updateBudget(Long budgetId, UpdateBudgetRequest updateBudgetRequest) {

        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with id: " + budgetId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if(!budget.getUser().getId().equals(user.getId()))
            throw new AuthorizationException("Budget with id " + budgetId + "does not belong to you");

        if(updateBudgetRequest.getBudgetLimit() != 0){
            // Calculate the remaining amount after updating name and start amount
            double remainingAmount = budget.getCurrentAmount() + (updateBudgetRequest.getBudgetLimit() - budget.getStartAmount());

            // Ensure the remaining budget is not negative
            if (remainingAmount < 0) {
                throw new InsufficientFundsException("Updated budget would have negative funds");
            }

            budget.setStartAmount(updateBudgetRequest.getBudgetLimit());
            budget.setCurrentAmount(remainingAmount);

        }

        if(updateBudgetRequest.getBudgetName() != null)
            budget.setName(updateBudgetRequest.getBudgetName());

        budgetRepository.save(budget);
    }
    public List<GetBudgetsResponse> getMyBudgets(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<Budget> budgets = budgetRepository.findByUser(user);

        return budgets.stream()
                .map(budget -> new GetBudgetsResponse(
                        budget.getId(),
                        budget.getName(),
                        budget.getStartAmount(),
                        budget.getCurrentAmount(),
                        budget.getEndDate(),
                        budget.getStartDate()
                ))
                .collect(Collectors.toList());

    }
}
