package com.example.georgestore.domain.exception;

public class NoOrdersFoundException extends RuntimeException {

    public NoOrdersFoundException() {
        super("No orders found");
    }

    public NoOrdersFoundException(String message) {
        super(message);
    }
}
