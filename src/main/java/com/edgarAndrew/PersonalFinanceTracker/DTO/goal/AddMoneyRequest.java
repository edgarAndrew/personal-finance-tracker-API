package com.edgarAndrew.PersonalFinanceTracker.DTO.goal;

public class AddMoneyRequest {
    private Long goalId;
    private double amount;

    public AddMoneyRequest() {
    }

    public AddMoneyRequest(Long goalId, double amount) {
        this.goalId = goalId;
        this.amount = amount;
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
