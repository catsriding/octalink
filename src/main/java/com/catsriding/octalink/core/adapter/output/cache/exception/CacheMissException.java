package com.catsriding.octalink.core.adapter.output.cache.exception;

public class CacheMissException extends RuntimeException {

    public CacheMissException() {
    }

    public CacheMissException(String message) {
        super(message);
    }

    public CacheMissException(String message, Throwable cause) {
        super(message, cause);
    }
}
