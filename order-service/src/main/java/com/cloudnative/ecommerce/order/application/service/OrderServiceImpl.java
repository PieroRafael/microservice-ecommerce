package com.cloudnative.ecommerce.order.application.service;

import com.cloudnative.ecommerce.order.domain.exception.OrderNotFoundException;
import com.cloudnative.ecommerce.order.domain.model.Order;
import com.cloudnative.ecommerce.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository; // Inyectamos el PUERTO, no el adaptador

    @Override
    @Transactional
    public Order createOrder(Order order) {
        // Aquí podríamos añadir lógica extra o validaciones de negocio adicionales
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
