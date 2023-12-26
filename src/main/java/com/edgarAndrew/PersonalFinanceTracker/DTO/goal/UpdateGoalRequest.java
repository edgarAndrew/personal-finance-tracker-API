package com.edgarAndrew.PersonalFinanceTracker.DTO.goal;

import java.time.LocalDate;

public class UpdateGoalRequest {

    private String name;
    private Double targetAmount;
    private LocalDate endDate;

    // Constructors, getters, and setters...

    // Constructors
    public UpdateGoalRequest() {
        // Default constructor
    }

    public UpdateGoalRequest(String name, Double targetAmount, LocalDate endDate) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.endDate = endDate;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

