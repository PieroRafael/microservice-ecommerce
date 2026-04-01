package com.cloudnative.ecommerce.inventory.infrastructure.persistence.mapper;

import com.cloudnative.ecommerce.inventory.domain.model.Inventory;
import com.cloudnative.ecommerce.inventory.infrastructure.persistence.entity.InventoryEntity;
import org.springframework.stereotype.Component;

@Component
public class InventoryPersistenceMapper {

    public Inventory toDomain(InventoryEntity entity) {
        if (entity == null)
            return null;
        return Inventory.builder()
                .id(entity.getId())
                .skuCode(entity.getSkuCode())
                .quantity(entity.getQuantity())
                .reserved(entity.getReserved())
                .version(entity.getVersion())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public InventoryEntity toEntity(Inventory domain) {
        if (domain == null)
            return null;
        return InventoryEntity.builder()
                .id(domain.getId())
                .skuCode(domain.getSkuCode())
                .quantity(domain.getQuantity())
                .reserved(domain.getReserved())
                .version(domain.getVersion())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
