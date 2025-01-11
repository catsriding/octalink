package com.catsriding.octalink.core.adapter.output.persistence.exception;

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
