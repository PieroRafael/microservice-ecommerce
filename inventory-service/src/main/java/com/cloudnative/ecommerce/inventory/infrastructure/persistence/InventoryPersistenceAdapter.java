package com.cloudnative.ecommerce.inventory.infrastructure.persistence;

import com.cloudnative.ecommerce.inventory.domain.model.Inventory;
import com.cloudnative.ecommerce.inventory.domain.repository.InventoryRepository;
import com.cloudnative.ecommerce.inventory.infrastructure.persistence.mapper.InventoryPersistenceMapper;
import com.cloudnative.ecommerce.inventory.infrastructure.persistence.repository.InventoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador de Persistencia que implementa el Puerto de Salida (Output Port).
 * Aísla al dominio de la tecnología JPA.
 */
@Component
@RequiredArgsConstructor
public class InventoryPersistenceAdapter implements InventoryRepository {

    private final InventoryJpaRepository jpaRepository;
    private final InventoryPersistenceMapper mapper;

    @Override
    public Inventory save(Inventory inventory) {
        var entity = mapper.toEntity(inventory);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Inventory> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Inventory> findBySkuCode(String skuCode) {
        return jpaRepository.findBySkuCode(skuCode).map(mapper::toDomain);
    }

    @Override
    public List<Inventory> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsBySkuCode(String skuCode) {
        return jpaRepository.existsBySkuCode(skuCode);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
