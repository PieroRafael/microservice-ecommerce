package com.cloudnative.ecommerce.order.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;
import com.cloudnative.ecommerce.order.domain.model.OrderStatus;

public record OrderResponse(
        UUID id,
        String orderNumber,
        String skuCode,
        Integer quantity,
        BigDecimal price,
        OrderStatus status,
        boolean isAvailable) {
}
