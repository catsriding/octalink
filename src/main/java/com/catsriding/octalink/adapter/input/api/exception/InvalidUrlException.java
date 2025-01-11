package com.catsriding.octalink.adapter.input.api.exception;

public class InvalidUrlException extends RuntimeException {

    public InvalidUrlException() {
    }

    public InvalidUrlException(String message) {
        super(message);
    }

    public InvalidUrlException(String message, Throwable cause) {
        super(message, cause);
    }
}
