package com.cloudnative.ecommerce.order.application.service;

import com.cloudnative.ecommerce.order.domain.model.Order;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order createOrder(Order order);

    Order getOrderById(UUID id);

    List<Order> getAllOrders();

    void deleteOrder(UUID id);
}
