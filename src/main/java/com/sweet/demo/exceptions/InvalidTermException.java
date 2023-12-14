package com.sweet.demo.exceptions;

public class InvalidTermException extends RuntimeException {

    public InvalidTermException(String message) {
        super(message);
    }
}