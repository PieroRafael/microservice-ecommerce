package com.cloudnative.ecommerce.inventory.infrastructure.rest.mapper;

import com.cloudnative.ecommerce.inventory.domain.model.Inventory;
import com.cloudnative.ecommerce.inventory.infrastructure.rest.dto.InventoryRequest;
import com.cloudnative.ecommerce.inventory.infrastructure.rest.dto.InventoryResponse;
import org.springframework.stereotype.Component;

@Component
public class InventoryRestMapper {

    public Inventory toDomain(InventoryRequest request) {
        return Inventory.builder()
                .skuCode(request.skuCode())
                .quantity(request.quantity())
                .reserved(0)
                .build();
    }

    public InventoryResponse toResponse(Inventory domain) {
        return new InventoryResponse(
                domain.getId(),
                domain.getSkuCode(),
                domain.getQuantity(),
                domain.getReserved(),
                domain.getAvailableStock(),
                domain.getUpdatedAt());
    }
}
