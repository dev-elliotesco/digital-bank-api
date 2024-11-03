package com.dev_elliotesco.digital_bank.handlers;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
