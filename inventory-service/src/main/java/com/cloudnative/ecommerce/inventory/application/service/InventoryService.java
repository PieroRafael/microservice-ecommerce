/**
 * InventoryService - Input Port.
 * 
 * Define las operaciones permitidas sobre el inventario desde la perspectiva de la aplicación.
 * Incluye la capacidad de reducir stock de forma atómica ante eventos de órdenes.
 */
package com.cloudnative.ecommerce.inventory.application.service;

import com.cloudnative.ecommerce.inventory.domain.model.Inventory;

import java.util.List;

public interface InventoryService {

    Inventory createInventory(Inventory inventory);

    Inventory getBySkuCode(String skuCode);

    List<Inventory> getAll();

    boolean isInStock(String skuCode, int quantity);

    Inventory updateStock(String skuCode, int newQuantity);

    void reduceStock(String skuCode, int quantity);
}
