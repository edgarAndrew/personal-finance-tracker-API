package com.edgarAndrew.PersonalFinanceTracker.exceptions.transaction;

import java.util.Map;

public class GetTransactionByCategoryResponse {

    private String categoryName;
    private double percentage;
    private double amount;
    private String type;

    public GetTransactionByCategoryResponse() {
    }

    public GetTransactionByCategoryResponse(String categoryName, double percentage, double amount,String type) {
        this.categoryName = categoryName;
        this.percentage = percentage;
        this.amount = amount;
        this.type = type;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
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
}

