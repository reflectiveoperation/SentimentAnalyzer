package com.cristianperez.sentimentanalyzersample.logic;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
