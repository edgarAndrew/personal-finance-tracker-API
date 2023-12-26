package com.edgarAndrew.PersonalFinanceTracker.DTO.goal;

import java.time.LocalDate;

public class AddGoalRequest {

    private String name;
    private double targetAmount;
    private LocalDate startDate;
    private LocalDate endDate;


    public AddGoalRequest() {
        // Default constructor
    }

    public AddGoalRequest(String name, double targetAmount, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

