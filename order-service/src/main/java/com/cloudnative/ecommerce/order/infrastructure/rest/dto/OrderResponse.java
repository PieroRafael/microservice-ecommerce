package com.cloudnative.ecommerce.order.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String orderNumber,
        String skuCode,
        Integer quantity,
        BigDecimal price,
        boolean isAvailable) {
}
