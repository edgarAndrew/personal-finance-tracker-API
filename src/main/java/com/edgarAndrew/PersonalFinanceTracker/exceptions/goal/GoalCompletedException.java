package com.edgarAndrew.PersonalFinanceTracker.exceptions.goal;

public class GoalCompletedException extends RuntimeException{
    public GoalCompletedException(String message){
        super(message);
    }
}
