/**
 * InventoryService - Input Port.
 * 
 * Define las operaciones permitidas sobre el inventario desde la perspectiva de la aplicación.
 * Incluye la capacidad de reducir stock de forma atómica ante eventos de órdenes.
 */
package com.cloudnative.ecommerce.inventory.application.service;

import com.cloudnative.ecommerce.inventory.domain.model.Inventory;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    Inventory createInventory(Inventory inventory);

    Inventory getBySkuCode(String skuCode);

    List<Inventory> getAll();

    boolean isInStock(String skuCode, int quantity);

    Inventory updateStock(String skuCode, int newQuantity);

    /**
     * Reduce el stock de un producto específico basado en un evento de orden.
     * @param orderId Identificador único de la orden (para idempotencia)
     * @param skuCode Código del producto
     * @param quantity Cantidad a reducir
     */
    void reduceStock(UUID orderId, String skuCode, int quantity);
}
