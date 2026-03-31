/**
 * OrderServiceImpl - Application Service.
 *
 * Implementación del caso de uso de órdenes utilizando el patrón Transactional Outbox
 * para asegurar la consistencia entre la base de datos y Kafka.
 */
package com.cloudnative.ecommerce.order.application.service;

import com.cloudnative.ecommerce.order.domain.exception.OrderNotFoundException;
import com.cloudnative.ecommerce.order.domain.exception.ProductNotFoundException;
import com.cloudnative.ecommerce.order.domain.model.Order;
import com.cloudnative.ecommerce.order.domain.model.OrderStatus;
import com.cloudnative.ecommerce.order.domain.model.OutboxEvent;
import com.cloudnative.ecommerce.order.domain.port.out.OutboxRepository;
import com.cloudnative.ecommerce.order.domain.port.out.ProductGateway;
import com.cloudnative.ecommerce.order.domain.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductGateway productGateway;
    private final OutboxRepository outboxRepository; // Reemplazamos publicación directa por persistencia Outbox
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public Order createOrder(Order order) {
        log.info("Iniciando creación de orden para el producto: {}", order.getSkuCode());

        // 1. Validación de negocio externa
        String sku = order.getSkuCode();
        if (!productGateway.existsBySku(sku)) {
            throw new ProductNotFoundException(sku);
        }

        // 2. Persistencia de la Orden
        order.setStatus(OrderStatus.PENDING_INVENTORY);
        Order savedOrder = orderRepository.save(order);

        // 3. Persistencia del Evento en el Outbox (Atómico con el paso 2)
        try {
            String payload = objectMapper.writeValueAsString(savedOrder);
            OutboxEvent outboxEvent = OutboxEvent.create(
                    savedOrder.getId(),
                    "ORDER_CREATED",
                    payload);
            outboxRepository.save(outboxEvent);
            log.info("Evento de orden guardado en el outbox para la orden: {}", savedOrder.getId());
        } catch (JsonProcessingException e) {
            log.error("Error serializando el evento para la orden: {}", savedOrder.getId(), e);
            throw new RuntimeException("Error en el procesamiento del evento Outbox", e);
        }

        return savedOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void completeOrder(UUID id) {
        Order order = getOrderById(id);
        order.complete();
        orderRepository.save(order);
        log.info("Orden {} marcada como COMPLETED.", id);
    }

    @Override
    @Transactional
    public void cancelOrder(UUID id) {
        Order order = getOrderById(id);
        order.cancel();
        orderRepository.save(order);
        log.warn("Orden {} marcada como CANCELLED.", id);
    }
}
