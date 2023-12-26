package com.edgarAndrew.PersonalFinanceTracker.DTO.budget;

public class UpdateBudgetRequest {
    private String budgetName;
    private double budgetLimit;

    public UpdateBudgetRequest() {
    }

    public UpdateBudgetRequest(String budgetName) {
        this.budgetName = budgetName;
        this.budgetLimit = 0;
    }

    public UpdateBudgetRequest(String budgetName, double budgetLimit) {
        this.budgetName = budgetName;
        this.budgetLimit = budgetLimit;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(double budgetLimit) {
        this.budgetLimit = budgetLimit;
    }
}
