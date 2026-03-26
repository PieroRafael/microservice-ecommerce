/**
 * OrderServiceImpl - Application Service.
 *
 * Esta clase actúa como el orquestador principal del caso de uso de órdenes.
 * Coordina la persistencia en base de datos, la validación contra otros microservicios
 * a través de puertos de salida y la emisión de eventos de dominio asíncronos.
 */
package com.cloudnative.ecommerce.order.application.service;

import com.cloudnative.ecommerce.order.domain.event.OrderCreatedEvent;
import com.cloudnative.ecommerce.order.domain.exception.OrderNotFoundException;
import com.cloudnative.ecommerce.order.domain.exception.ProductNotFoundException;
import com.cloudnative.ecommerce.order.domain.model.Order;
import com.cloudnative.ecommerce.order.domain.port.out.OrderEventPublisher;
import com.cloudnative.ecommerce.order.domain.port.out.ProductGateway;
import com.cloudnative.ecommerce.order.domain.repository.OrderRepository;
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
    private final ProductGateway productGateway; // Inyectamos el PUERTO de comunicación con productos
    private final OrderEventPublisher eventPublisher; // Nuevo puerto para eventos asíncronos

    @Override
    @Transactional
    public Order createOrder(Order order) {
        log.info("Iniciando creación de orden para el producto: {}", order.getSkuCode());

        // Validación de negocio: ¿Existe el producto en el catálogo?
        // El skuCode que envía el cliente se valida contra el microservicio de producto
        String sku = order.getSkuCode();
        if (!productGateway.existsBySku(sku)) {
            throw new ProductNotFoundException(sku);
        }

        Order savedOrder = orderRepository.save(order);

        // Publicar evento de dominio a Kafka
        var event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getSkuCode(),
                savedOrder.getQuantity(),
                savedOrder.getPrice(),
                java.time.LocalDateTime.now());
        eventPublisher.publishOrderCreated(event);

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
}
