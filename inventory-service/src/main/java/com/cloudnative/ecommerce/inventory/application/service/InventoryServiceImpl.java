/**
 * InventoryServiceImpl - Application Service.
 * 
 * Implementación del puerto de entrada InventoryService.
 * Coordina la lógica de negocio para la gestión de stocks, asegurando la consistencia
 * transaccional y validando invariantes (como stock suficiente) antes de persistir.
 */
package com.cloudnative.ecommerce.inventory.application.service;

import com.cloudnative.ecommerce.inventory.domain.exception.InventoryNotFoundException;
import com.cloudnative.ecommerce.inventory.domain.model.Inventory;
import com.cloudnative.ecommerce.inventory.domain.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public Inventory createInventory(Inventory inventory) {
        log.info("Creating inventory for SKU: {}", inventory.getSkuCode());
        inventory.setUpdatedAt(LocalDateTime.now());
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public Inventory getBySkuCode(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new InventoryNotFoundException(skuCode));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode, int quantity) {
        return inventoryRepository.findBySkuCode(skuCode)
                .map(inv -> inv.hasEnoughStock(quantity))
                .orElse(false);
    }

    @Override
    @Transactional
    public Inventory updateStock(String skuCode, int newQuantity) {
        log.info("Updating stock for SKU: {} to quantity: {}", skuCode, newQuantity);
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new InventoryNotFoundException(skuCode));
        inventory.setQuantity(newQuantity);
        inventory.setUpdatedAt(LocalDateTime.now());
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public void reduceStock(String skuCode, int quantity) {
        log.info("Reduciendo stock para SKU: {} en cantidad: {}", skuCode, quantity);
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new InventoryNotFoundException(skuCode));

        // Delegar lógica al dominio
        inventory.decreaseStock(quantity);
        
        inventoryRepository.save(inventory);
        log.info("Stock reducido con éxito para SKU: {}. Nuevo total: {}", skuCode, inventory.getQuantity());
    }
}
