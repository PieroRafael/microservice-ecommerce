package com.cloudnative.ecommerce.order.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private UUID id;
    private String orderNumber;
    private String skuCode;
    private Integer quantity;
    private BigDecimal price;
    private boolean isAvailable; // Indica si el producto está disponible
}
