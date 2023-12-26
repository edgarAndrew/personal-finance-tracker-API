package com.edgarAndrew.PersonalFinanceTracker.exceptions.bank;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String message){
        super(message);
    }
}
