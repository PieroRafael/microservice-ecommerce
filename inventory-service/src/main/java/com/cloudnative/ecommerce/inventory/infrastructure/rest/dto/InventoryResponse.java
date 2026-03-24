package com.cloudnative.ecommerce.inventory.infrastructure.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryResponse(
        UUID id,
        String skuCode,
        Integer quantity,
        Integer reserved,
        Integer availableStock,
        LocalDateTime updatedAt) {
}
