package com.edgarAndrew.PersonalFinanceTracker.DTO.budget;

import java.time.LocalDate;

public class GetBudgetsResponse {
    private Long id;
    private String name;
    private double start_amount;
    private double current_amount;
    private LocalDate start_date;
    private LocalDate end_date;

    public GetBudgetsResponse(Long id, String name, double start_amount, double current_amount, LocalDate start_date, LocalDate end_date) {
        this.id = id;
        this.name = name;
        this.start_amount = start_amount;
        this.current_amount = current_amount;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStart_amount() {
        return start_amount;
    }

    public void setStart_amount(double start_amount) {
        this.start_amount = start_amount;
    }

    public double getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(double current_amount) {
        this.current_amount = current_amount;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }
}
