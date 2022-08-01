package com.crown.africa.customerservice.exception;

public class InvalidAccountNumberException extends RuntimeException {
    public InvalidAccountNumberException(String message) {
        super(message);
    }
}
