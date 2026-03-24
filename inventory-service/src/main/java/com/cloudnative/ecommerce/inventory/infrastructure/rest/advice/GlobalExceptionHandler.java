package com.cloudnative.ecommerce.inventory.infrastructure.rest.advice;

import com.cloudnative.ecommerce.inventory.domain.exception.InventoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InventoryNotFoundException.class)
    public ProblemDetail handleInventoryNotFound(InventoryNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Inventory Not Found");
        problem.setType(URI.create("https://ecommerce.com/errors/inventory-not-found"));
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Failed");
        problem.setTitle("Validation Error");
        problem.setType(URI.create("https://ecommerce.com/errors/validation"));
        problem.setProperty("timestamp", Instant.now());

        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> Map.of("field", err.getField(), "error", err.getDefaultMessage()))
                .toList();

        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        log.error("Unhandled exception caught: {}", ex.getClass().getName(), ex);
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ha ocurrido un error inesperado.");
        problem.setTitle("Internal Server Error");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("dev-message", ex.getMessage());
        return problem;
    }
}
