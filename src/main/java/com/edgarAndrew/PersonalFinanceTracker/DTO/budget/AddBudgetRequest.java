package com.edgarAndrew.PersonalFinanceTracker.DTO.budget;

public class AddBudgetRequest {

    private String name;
    private double startAmount;

    public AddBudgetRequest() {
        // Default constructor
    }

    public AddBudgetRequest(String name, double startAmount) {
        this.name = name;
        this.startAmount = startAmount;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(double startAmount) {
        this.startAmount = startAmount;
    }
}
