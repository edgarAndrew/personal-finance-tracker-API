package com.edgarAndrew.PersonalFinanceTracker.DTO.transaction;

public class AddTransactionRequest {

    private Long bankAccountId;
    private double amount;
    private String type;
    private String category;
    private Boolean budget;

    public AddTransactionRequest() {

    }

    public AddTransactionRequest(Long bankAccountId, double amount, String type, String category,Boolean budget) {
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.budget = budget;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getBudget() {
        return budget;
    }

    public void setBudget(Boolean budget) {
        this.budget = budget;
    }
}
