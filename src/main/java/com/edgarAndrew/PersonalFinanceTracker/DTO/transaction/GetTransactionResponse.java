package com.edgarAndrew.PersonalFinanceTracker.DTO.transaction;

import com.edgarAndrew.PersonalFinanceTracker.models.Transaction;

import java.time.LocalDateTime;

public class GetTransactionResponse {
    private Long id;
    private double amount;
    private String type;
    private Transaction.Category category;
    private LocalDateTime date;

    public GetTransactionResponse() {
    }

    public GetTransactionResponse(Long id, double amount, String type, Transaction.Category category, LocalDateTime date) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Transaction.Category getCategory() {
        return category;
    }

    public void setCategory(Transaction.Category category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
