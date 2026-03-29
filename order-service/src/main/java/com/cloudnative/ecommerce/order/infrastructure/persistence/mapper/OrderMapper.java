package com.cloudnative.ecommerce.order.infrastructure.persistence.mapper;

import com.cloudnative.ecommerce.order.domain.model.Order;
import com.cloudnative.ecommerce.order.infrastructure.persistence.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toDomain(OrderEntity entity) {
        if (entity == null) {
            return null;
        }
        return Order.builder()
                .id(entity.getId())
                .orderNumber(entity.getOrderNumber())
                .skuCode(entity.getSkuCode())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .status(entity.getStatus())
                .build();
    }

    public OrderEntity toEntity(Order order) {
        if (order == null) {
            return null;
        }
        return OrderEntity.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .skuCode(order.getSkuCode())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .build();
    }
}
