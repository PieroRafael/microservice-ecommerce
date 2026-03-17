package com.cloudnative.ecommerce.order.infrastructure.rest.mapper;

import com.cloudnative.ecommerce.order.domain.model.Order;
import com.cloudnative.ecommerce.order.infrastructure.rest.dto.OrderRequest;
import com.cloudnative.ecommerce.order.infrastructure.rest.dto.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderRestMapper {

    public Order toDomain(OrderRequest request) {
        if (request == null)
            return null;
        return Order.builder()
                .orderNumber(request.getOrderNumber())
                .skuCode(request.getSkuCode())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }

    public OrderResponse toResponse(Order domain) {
        if (domain == null)
            return null;
        return OrderResponse.builder()
                .id(domain.getId())
                .orderNumber(domain.getOrderNumber())
                .skuCode(domain.getSkuCode())
                .quantity(domain.getQuantity())
                .price(domain.getPrice())
                .isAvailable(domain.hasQuantity())
                .build();
    }
}
