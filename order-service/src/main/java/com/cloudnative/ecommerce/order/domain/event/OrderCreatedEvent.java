/**
 * OrderCreatedEvent - Domain Event (Record).
 *
 * Representa un hecho inmutable ocurrido en el dominio: la creación exitosa de una orden.
 * Este record es utilizado para la comunicación asíncrona entre microservicios,
 * portando los datos esenciales del evento para consumidores interesados.
 */
package com.cloudnative.ecommerce.order.domain.event;

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
