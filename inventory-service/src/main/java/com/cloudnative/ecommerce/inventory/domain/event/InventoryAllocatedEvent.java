package com.cloudnative.ecommerce.inventory.domain.event;

import java.util.UUID;

/**
 * Evento emitido cuando el stock ha sido reservado/descontado con éxito.
 */
public record InventoryAllocatedEvent(
        UUID orderId,
        String skuCode,
        boolean success
) {}
