package com.cloudnative.ecommerce.order.domain.event;

import java.util.UUID;

public record InventoryFailedEvent(
        UUID orderId,
        String skuCode,
        boolean success,
        String reason
) {}
