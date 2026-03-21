package com.cloudnative.ecommerce.order.infrastructure.rest.advice;

import com.cloudnative.ecommerce.order.domain.exception.OrderNotFoundException;
import com.cloudnative.ecommerce.order.domain.exception.ProductNotFoundException;
import com.cloudnative.ecommerce.order.domain.exception.ServiceUnavailableException;
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

    @ExceptionHandler(OrderNotFoundException.class)
    public ProblemDetail handleOrderNotFound(OrderNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Order Not Found");
        problem.setType(URI.create("https://ecommerce.com/errors/not-found"));
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail handleProductNotFound(ProductNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Product Not Found");
        problem.setType(URI.create("https://ecommerce.com/errors/product-not-found"));
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

    @ExceptionHandler(ServiceUnavailableException.class)
    public ProblemDetail handleServiceUnavailable(ServiceUnavailableException ex) {
        log.warn("Service unavailable: {} — {}", ex.getServiceName(), ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        problem.setTitle("Service Unavailable");
        problem.setType(URI.create("https://ecommerce.com/errors/service-unavailable"));
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("service", ex.getServiceName());
        problem.setProperty("retryAfterSeconds", 10);
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        log.error("Unhandled exception caught: {}", ex.getClass().getName(), ex);
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un error inesperado.");
        problem.setTitle("Internal Server Error");
        problem.setProperty("timestamp", Instant.now());
        // En desarrollo podríamos añadir el mensaje real, en prod no por seguridad
        problem.setProperty("dev-message", ex.getMessage());
        return problem;
    }
}
