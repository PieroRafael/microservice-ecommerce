package com.cloudnative.ecommerce.inventory.infrastructure.persistence.repository;

import com.cloudnative.ecommerce.inventory.infrastructure.persistence.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryJpaRepository extends JpaRepository<InventoryEntity, UUID> {

    Optional<InventoryEntity> findBySkuCode(String skuCode);

    boolean existsBySkuCode(String skuCode);
}
