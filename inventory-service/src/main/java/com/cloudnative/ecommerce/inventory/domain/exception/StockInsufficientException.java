/**
 * StockInsufficientException - Domain Exception.
 * 
 * Lanzada cuando el inventario no tiene unidades suficientes para completar
 * una operación de reducción de stock.
 */
package com.cloudnative.ecommerce.inventory.domain.exception;

import lombok.Getter;

@Getter
public class StockInsufficientException extends RuntimeException {
    private final String skuCode;
    private final int requested;
    private final int available;

    public StockInsufficientException(String skuCode, int requested, int available) {
        super(String.format("Stock insuficiente para SKU %s. Solicitado: %d, Disponible: %d", 
                skuCode, requested, available));
        this.skuCode = skuCode;
        this.requested = requested;
        this.available = available;
    }

    public StockInsufficientException(String skuCode, int requested) {
        super(String.format("No se puede descontar %d unidades del SKU %s. Stock no disponible o nulo.", 
                requested, skuCode));
        this.skuCode = skuCode;
        this.requested = requested;
        this.available = 0;
    }
}
