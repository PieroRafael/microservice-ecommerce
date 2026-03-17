package com.cloudnative.ecommerce.product.domain.exception;

import java.util.UUID;

// @Excepcion: Excepción personalizada para manejar el caso en que un producto no se encuentra.
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(UUID id) {
        super("Product not found with id: " + id);
    }
}
