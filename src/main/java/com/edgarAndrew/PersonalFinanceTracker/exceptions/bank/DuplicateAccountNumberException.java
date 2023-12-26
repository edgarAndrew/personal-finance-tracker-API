package com.edgarAndrew.PersonalFinanceTracker.exceptions.bank;

public class DuplicateAccountNumberException extends RuntimeException{
    public DuplicateAccountNumberException(String message){
        super(message);
    }
}
