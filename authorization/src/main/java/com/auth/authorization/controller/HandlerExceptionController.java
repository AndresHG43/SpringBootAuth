package com.auth.authorization.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class HandlerExceptionController extends Controller {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception  e) {
        return ResponseEntity.
                internalServerError().
                body(message.
                        error("500", e.getMessage())
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException  e) {
        return ResponseEntity.
                internalServerError().
                body(message.
                        error("500", e.getMessage())
                );
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException  e) {
        return ResponseEntity.
                internalServerError().
                body(message.
                        error("500", e.getMessage())
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> EntityNotFoundException(EntityNotFoundException  e) {
        return ResponseEntity.
                status(HttpStatus.NOT_FOUND).
                body(message.
                        error("404", e.getMessage())
                );
    }
}
