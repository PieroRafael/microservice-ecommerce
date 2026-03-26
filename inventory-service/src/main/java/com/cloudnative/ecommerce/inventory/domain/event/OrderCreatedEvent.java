/**
 * OrderCreatedEvent - Domain Event (Record).
 * 
 * Replicación del evento de dominio emitido por order-service.
 * Este record se utiliza para la deserialización de mensajes entrantes desde Kafka
 * y porta la información necesaria para actualizar el inventario.
 */
package com.cloudnative.ecommerce.inventory.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        String skuCode,
        Integer quantity,
        BigDecimal totalPrice,
        LocalDateTime createdAt) {
}
