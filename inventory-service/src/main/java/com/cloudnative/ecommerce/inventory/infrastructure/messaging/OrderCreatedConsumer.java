/**
 * OrderCreatedConsumer - Infrastructure Adapter (Input).
 * 
 * Suscriptor de Kafka que escucha eventos de creación de órdenes.
 * Implementa resiliencia mediante @RetryableTopic para reintentos no bloqueantes
 * y un @DltHandler para capturar fallos definitivos.
 */
package com.cloudnative.ecommerce.inventory.infrastructure.messaging;

import com.cloudnative.ecommerce.inventory.application.service.InventoryService;
import com.cloudnative.ecommerce.inventory.domain.event.InventoryAllocatedEvent;
import com.cloudnative.ecommerce.inventory.domain.event.InventoryFailedEvent;
import com.cloudnative.ecommerce.inventory.domain.event.OrderCreatedEvent;
import com.cloudnative.ecommerce.inventory.domain.exception.StockInsufficientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer {

    private final InventoryService inventoryService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000, multiplier = 2.0),
            autoCreateTopics = "true",
            retryTopicSuffix = "-retry",
            dltTopicSuffix = "-dlt",
            exclude = {
                com.cloudnative.ecommerce.inventory.domain.exception.StockInsufficientException.class,
                com.cloudnative.ecommerce.inventory.domain.exception.InventoryNotFoundException.class
            }
    )
    @KafkaListener(topics = "order-placed-topic", groupId = "inventory-group")
    public void consumeOrderCreated(OrderCreatedEvent event) {
        log.info("Evento recibido de Kafka: {}", event);
        try {
            inventoryService.reduceStock(event.orderId(), event.skuCode(), event.quantity());
            log.info("Inventario actualizado para la orden: {}", event.orderId());

            // ✅ Escenario de Éxito (Saga - Happy Path)
            InventoryAllocatedEvent successEvent = new InventoryAllocatedEvent(
                    event.orderId(),
                    event.skuCode(),
                    true
            );
            kafkaTemplate.executeInTransaction(kt -> {
                kt.send("inventory-allocated-topic", event.orderId().toString(), successEvent);
                return true;
            });

        } catch (StockInsufficientException e) {
            log.warn("Stock insuficiente para orden {}: {}", event.orderId(), e.getMessage());

            // ❌ Escenario de Fallo (Saga - Compensating Transaction)
            InventoryFailedEvent failedEvent = new InventoryFailedEvent(
                    event.orderId(),
                    event.skuCode(),
                    false,
                    e.getMessage()
            );
            kafkaTemplate.executeInTransaction(kt -> {
                kt.send("inventory-failed-topic", event.orderId().toString(), failedEvent);
                return true;
            });

        } catch (Exception e) {
            log.error("Error al procesar el evento de Kafka para la orden: {}. Error: {}",
                    event.orderId(), e.getMessage());
            throw e; // Lanza la excepción para que @RetryableTopic actúe (errores de red/base de datos)
        }
    }

    @DltHandler
    public void handleDlt(OrderCreatedEvent event, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.error("🛑 Evento movido a DLT (Topic: {}): {}", topic, event);
    }
}
