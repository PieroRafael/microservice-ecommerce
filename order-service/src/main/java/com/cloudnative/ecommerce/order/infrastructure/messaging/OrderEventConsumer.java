package com.cloudnative.ecommerce.order.infrastructure.messaging;

import com.cloudnative.ecommerce.order.application.service.OrderService;
import com.cloudnative.ecommerce.order.domain.event.InventoryAllocatedEvent;
import com.cloudnative.ecommerce.order.domain.event.InventoryFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "inventory-allocated-topic", groupId = "order-group")
    public void consumeInventoryAllocated(InventoryAllocatedEvent event) {
        log.info("✅ Stock reservado exitosamente (Saga Happy Path) para la orden: {}. Completando orden.", event.orderId());
        orderService.completeOrder(event.orderId());
    }

    @KafkaListener(topics = "inventory-failed-topic", groupId = "order-group")
    public void consumeInventoryFailed(InventoryFailedEvent event) {
        log.warn("❌ Fallo de reserva de stock para la orden: {}. Razón: {}. Ejecutando transacción compensatoria (CANCELLED).", event.orderId(), event.reason());
        orderService.cancelOrder(event.orderId());
    }
}
