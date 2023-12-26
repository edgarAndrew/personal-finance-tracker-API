package com.edgarAndrew.PersonalFinanceTracker.exceptions.goal;

public class GoalNotFoundException extends RuntimeException{
    public GoalNotFoundException(String message){
        super(message);
    }
}
