package com.cloudnative.ecommerce.order.domain.exception;

import java.util.UUID;

// @Excepcion: Excepción personalizada para manejar el caso en que una orden no se encuentra.
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(UUID id) {
        super("Order not found with id: " + id);
    }
}
