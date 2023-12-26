package com.edgarAndrew.PersonalFinanceTracker.exceptions.bank;

public class BankNotFoundException extends RuntimeException {
    public BankNotFoundException(String message){
        super(message);
    }
}
