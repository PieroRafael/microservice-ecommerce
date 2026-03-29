package com.cloudnative.ecommerce.inventory.domain.event;

import java.util.UUID;

/**
 * Evento emitido cuando no hay stock suficiente para procesar la orden.
 */
public record InventoryFailedEvent(
        UUID orderId,
        String skuCode,
        boolean success,
        String reason
) {}
