package com.catsriding.octalink.adapter.output.persistence.exception;

public class UrlEntityNotFoundException extends RuntimeException {

    public UrlEntityNotFoundException() {
    }

    public UrlEntityNotFoundException(String message) {
        super(message);
    }

    public UrlEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
