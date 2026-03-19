package com.cloudnative.ecommerce.order.domain.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(UUID id) {
        super("El producto con ID " + id + " no existe en el catálogo");
    }
}
