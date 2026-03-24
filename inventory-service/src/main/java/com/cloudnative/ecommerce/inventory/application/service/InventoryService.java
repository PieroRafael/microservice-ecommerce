package com.cloudnative.ecommerce.inventory.application.service;

import com.cloudnative.ecommerce.inventory.domain.model.Inventory;

import java.util.List;

/**
 * Puerto de Entrada (Input Port) para operaciones de inventario.
 */
public interface InventoryService {

    Inventory createInventory(Inventory inventory);

    Inventory getBySkuCode(String skuCode);

    List<Inventory> getAll();

    boolean isInStock(String skuCode, int quantity);

    Inventory updateStock(String skuCode, int newQuantity);
}
