package com.catsriding.octalink.core.adapter.output.cache.exception;

public class RedisUrlCacheFailureException extends RuntimeException {

    public RedisUrlCacheFailureException() {
    }

    public RedisUrlCacheFailureException(String message) {
        super(message);
    }

    public RedisUrlCacheFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
