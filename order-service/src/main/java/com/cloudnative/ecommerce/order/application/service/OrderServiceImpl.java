package com.cloudnative.ecommerce.order.application.service;

import com.cloudnative.ecommerce.order.domain.exception.OrderNotFoundException;
import com.cloudnative.ecommerce.order.domain.exception.ProductNotFoundException;
import com.cloudnative.ecommerce.order.domain.model.Order;
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

    @Override
    @Transactional
    public Order createOrder(Order order) {
        log.info("Iniciando creación de orden para el producto: {}", order.getSkuCode());
        
        // Validación de negocio: ¿Existe el producto en el catálogo?
        // En este ejemplo, el skuCode que envía el cliente debe ser un UUID válido
        UUID productId = UUID.fromString(order.getSkuCode());
        if (!productGateway.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }

        return orderRepository.save(order);
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
