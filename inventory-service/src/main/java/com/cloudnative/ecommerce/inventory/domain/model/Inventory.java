package com.cloudnative.ecommerce.inventory.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Modelo de dominio puro para Inventario.
 * No depende de ninguna tecnología de infraestructura (JPA, Feign, etc.).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    private UUID id;
    private String skuCode;
    private Integer quantity;
    private Integer reserved;
    private LocalDateTime updatedAt;

    /**
     * Calcula el stock disponible (cantidad total - reservados).
     */
    public int getAvailableStock() {
        int qty = quantity != null ? quantity : 0;
        int res = reserved != null ? reserved : 0;
        return qty - res;
    }

    /**
     * Verifica si hay stock suficiente para una cantidad solicitada.
     */
    public boolean hasEnoughStock(int requestedQuantity) {
        return getAvailableStock() >= requestedQuantity;
    }

    /**
     * Reserva una cantidad de stock (para órdenes pendientes de pago).
     */
    public void reserveStock(int amount) {
        if (!hasEnoughStock(amount)) {
            throw new IllegalStateException(
                    "Stock insuficiente para SKU " + skuCode
                            + ". Disponible: " + getAvailableStock()
                            + ", Solicitado: " + amount);
        }
        this.reserved = (this.reserved != null ? this.reserved : 0) + amount;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Descuenta stock definitivamente (cuando la orden se confirma).
     */
    public void decreaseStock(int amount) {
        if (quantity == null || quantity < amount) {
            throw new com.cloudnative.ecommerce.inventory.domain.exception.StockInsufficientException(skuCode, amount);
        }
        this.quantity -= amount;
        this.updatedAt = LocalDateTime.now();
    }
}
