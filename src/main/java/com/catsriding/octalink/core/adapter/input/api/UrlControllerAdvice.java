package com.catsriding.octalink.core.adapter.input.api;

import com.catsriding.octalink.core.adapter.input.api.exception.InvalidUrlException;
import com.catsriding.octalink.core.adapter.output.persistence.exception.UrlEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class UrlControllerAdvice {

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<?> handleException(InvalidUrlException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UrlEntityNotFoundException.class)
    public ResponseEntity<?> handleException(UrlEntityNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
