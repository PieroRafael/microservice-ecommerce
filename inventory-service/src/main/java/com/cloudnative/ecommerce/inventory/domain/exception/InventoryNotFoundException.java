package com.cloudnative.ecommerce.inventory.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(String skuCode) {
        super("Inventory not found for SKU: " + skuCode);
    }
}
