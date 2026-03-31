/**
 * OutboxPollingPublisher - Infrastructure Component (Relay).
 * 
 * Implementación del job periódico encargado de recuperar eventos de la base de datos
 * y publicarlos en Kafka, garantizando la consistencia eventual y la entrega fiable.
 */
package com.cloudnative.ecommerce.order.infrastructure.messaging;

import com.cloudnative.ecommerce.order.domain.event.OrderCreatedEvent;
import com.cloudnative.ecommerce.order.domain.model.Order;
import com.cloudnative.ecommerce.order.domain.model.OutboxEvent;
import com.cloudnative.ecommerce.order.domain.model.OutboxStatus;
import com.cloudnative.ecommerce.order.domain.port.out.OrderEventPublisher;
import com.cloudnative.ecommerce.order.domain.port.out.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OutboxPollingPublisher - Infrastructure Adapter (Relay).
 * 
 * Este componente implementa el proceso de 'Relay' del patrón Outbox.
 * Busca periódicamente eventos PENDING en la base de datos y los publica en Kafka.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPollingPublisher {

    private final OutboxRepository outboxRepository;
    private final OrderEventPublisher kafkaPublisher;
    private final ObjectMapper objectMapper;

    /**
     * Proceso de Polling programado cada 1 segundo (fixedRate = 1000).
     */
    @Scheduled(fixedRate = 1000)
    @Transactional
    public void publishPendingEvents() {
        List<OutboxEvent> pendingEvents = outboxRepository.findByStatus(OutboxStatus.PENDING);
        
        if (pendingEvents.isEmpty()) {
            return;
        }

        log.info("Encontrados {} eventos pendientes en el outbox.", pendingEvents.size());

        for (OutboxEvent event : pendingEvents) {
            try {
                // 1. Reconstruir el evento desde el payload
                // En OrderServiceImpl guardamos la orden completa como payload
                Order order = objectMapper.readValue(event.payload(), Order.class);
                
                OrderCreatedEvent domainEvent = new OrderCreatedEvent(
                        order.getId(),
                        order.getSkuCode(),
                        order.getQuantity(),
                        order.getPrice(),
                        event.createdAt()
                );

                // 2. Publicar en Kafka (usa el adaptador existente)
                kafkaPublisher.publishOrderCreated(domainEvent);

                // 3. Marcar como procesado exitosamente
                OutboxEvent processedEvent = new OutboxEvent(
                        event.id(),
                        event.aggregateId(),
                        event.eventType(),
                        event.payload(),
                        OutboxStatus.SENT,
                        event.createdAt(),
                        LocalDateTime.now()
                );
                outboxRepository.save(processedEvent);
                
                log.info("Evento [Type: {}] de la orden [{}] publicado exitosamente en Kafka.", 
                        event.eventType(), event.aggregateId());

            } catch (Exception e) {
                log.error("Error procesando evento {} del outbox: {}", event.id(), e.getMessage());
                // Podríamos implementar aquí un contador de intentos antes de marcar como FAILED definitivamente
            }
        }
    }
}

