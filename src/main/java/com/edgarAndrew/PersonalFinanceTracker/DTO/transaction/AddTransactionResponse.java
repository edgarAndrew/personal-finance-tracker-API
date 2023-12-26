package com.edgarAndrew.PersonalFinanceTracker.DTO.transaction;


import java.time.LocalDateTime;

public class AddTransactionResponse {

    private String accountNumber;
    private String message;
    private LocalDateTime timestamp;

    public AddTransactionResponse(String accountNumber, String message) {
        this.accountNumber = accountNumber;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public AddTransactionResponse() {
    }
}
