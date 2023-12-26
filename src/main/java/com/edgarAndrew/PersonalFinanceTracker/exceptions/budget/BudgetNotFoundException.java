package com.edgarAndrew.PersonalFinanceTracker.exceptions.budget;

public class BudgetNotFoundException extends RuntimeException{
    public BudgetNotFoundException(String message){
        super(message);
    }
}
