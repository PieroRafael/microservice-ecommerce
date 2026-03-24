package com.cloudnative.ecommerce.inventory.domain.repository;

import com.cloudnative.ecommerce.inventory.domain.model.Inventory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de Salida (Output Port) para la persistencia de inventario.
 * El dominio define qué necesita, la infraestructura lo implementa.
 */
public interface InventoryRepository {

    Inventory save(Inventory inventory);

    Optional<Inventory> findById(UUID id);

    Optional<Inventory> findBySkuCode(String skuCode);

    List<Inventory> findAll();

    boolean existsBySkuCode(String skuCode);

    void deleteById(UUID id);
}
