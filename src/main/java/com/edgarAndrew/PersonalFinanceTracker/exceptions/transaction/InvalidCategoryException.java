package com.edgarAndrew.PersonalFinanceTracker.exceptions.transaction;

public class InvalidCategoryException extends RuntimeException{
    public InvalidCategoryException(String message){
        super(message);
    }
}
