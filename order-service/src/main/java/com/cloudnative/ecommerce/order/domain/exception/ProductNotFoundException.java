package com.cloudnative.ecommerce.order.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(UUID id) {
        super("El producto con ID " + id + " no existe en el catálogo");
    }

    public ProductNotFoundException(String sku) {
        super("El producto con SKU " + sku + " no existe en el catálogo");
    }
}
