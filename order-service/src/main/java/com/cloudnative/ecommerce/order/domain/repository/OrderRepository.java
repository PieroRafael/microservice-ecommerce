package com.cloudnative.ecommerce.order.domain.repository;

import com.cloudnative.ecommerce.order.domain.model.Order;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

// @Puerto: Esta interfaz define el contrato de operaciones que el Dominio requiere y que la Infraestructura debe implementar.
public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAll();

    void deleteById(UUID id);
}
