/**
 * OrderCreatedConsumer - Infrastructure Adapter (Input).
 * 
 * Suscriptor de Kafka que escucha eventos de creación de órdenes.
 * Actúa como punto de entrada asíncrono, delegando la lógica de actualización
 * de inventario al servicio de aplicación correspondiente.
 */
package com.cloudnative.ecommerce.inventory.infrastructure.messaging;

import com.cloudnative.ecommerce.inventory.application.service.InventoryService;
import com.cloudnative.ecommerce.inventory.domain.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = "order-placed-topic",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeOrderCreated(OrderCreatedEvent event) {
        log.info("Evento recibido de Kafka: OrderCreatedEvent con ID: {} para SKU: {}", 
                event.orderId(), event.skuCode());
        
        try {
            inventoryService.reduceStock(event.skuCode(), event.quantity());
            log.info("Inventario actualizado para la orden: {}", event.orderId());
        } catch (Exception e) {
            log.error("Error al procesar el evento de Kafka para la orden: {}. Error: {}", 
                    event.orderId(), e.getMessage());
            // Nota: Aquí se podría lanzar una excepción para que el ErrorHandler de Kafka
            // reintente la operación o mueva el mensaje a una DLQ.
            throw e; 
        }
    }
}
