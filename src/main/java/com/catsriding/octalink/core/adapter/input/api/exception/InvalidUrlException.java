package com.catsriding.octalink.core.adapter.input.api.exception;

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
