package com.dev_elliotesco.digital_bank.handlers;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String message) {
        super(message);
    }
}
