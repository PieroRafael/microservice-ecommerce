package com.cloudnative.ecommerce.order.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

// @Excepcion: Excepción personalizada para manejar el caso en que una orden no se encuentra.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(UUID id) {
        super("Order not found with id: " + id);
    }
}
