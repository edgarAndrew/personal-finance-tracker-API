package com.edgarAndrew.PersonalFinanceTracker.exceptions.transaction;

public class InvalidDateCombinationException extends RuntimeException{
    public InvalidDateCombinationException(String message){
        super(message);
    }
}
