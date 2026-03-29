package com.cloudnative.ecommerce.order.domain.event;

import java.util.UUID;

public record InventoryAllocatedEvent(
        UUID orderId,
        String skuCode,
        boolean success
) {}
