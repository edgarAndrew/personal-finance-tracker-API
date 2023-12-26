package com.edgarAndrew.PersonalFinanceTracker.exceptions.budget;

public class DuplicateBudgetException extends RuntimeException{
    public DuplicateBudgetException(String message){
        super(message);
    }
}
