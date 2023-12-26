package com.edgarAndrew.PersonalFinanceTracker.DTO.bank;

public class AddBankAccountRequest {
    private String accountNumber;

    private double initialBalance;

    public AddBankAccountRequest(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.initialBalance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public AddBankAccountRequest() {
    }
}
