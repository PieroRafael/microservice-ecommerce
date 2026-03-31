/**
 * OutboxEvent - Domain Model (Record).
 * 
 * Representa la intención de emitir un evento de dominio, persistida atómicamente con el cambio de estado del negocio.
 */
package com.cloudnative.ecommerce.order.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * OutboxEvent - Domain Model.
 * 
 * Representa un evento persistido pendiente de envío a Kafka.
 * Se utiliza el patrón Record de Java 21 para inmutabilidad garantizada.
 */
public record OutboxEvent(
        UUID id,
        UUID aggregateId,
        String eventType,
        String payload,
        OutboxStatus status,
        LocalDateTime createdAt,
        LocalDateTime processedAt) {
    public static OutboxEvent create(UUID aggregateId, String eventType, String payload) {
        return new OutboxEvent(
                UUID.randomUUID(),
                aggregateId,
                eventType,
                payload,
                OutboxStatus.PENDING,
                LocalDateTime.now(),
                null);
    }
}
