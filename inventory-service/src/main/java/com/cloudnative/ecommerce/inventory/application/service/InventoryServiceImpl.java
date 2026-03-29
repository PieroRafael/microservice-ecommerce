package com.cloudnative.ecommerce.inventory.application.service;

import com.cloudnative.ecommerce.inventory.domain.exception.InventoryNotFoundException;
import com.cloudnative.ecommerce.inventory.domain.model.Inventory;
import com.cloudnative.ecommerce.inventory.domain.repository.InventoryRepository;
import com.cloudnative.ecommerce.inventory.infrastructure.persistence.entity.ProcessedOrderEntity;
import com.cloudnative.ecommerce.inventory.infrastructure.persistence.repository.ProcessedOrderJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.lang.NonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProcessedOrderJpaRepository processedOrderRepository;

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
    public void reduceStock(UUID orderId, String skuCode, int quantity) {
        log.info("Intento de reducción de stock para SKU: {} por orden: {}", skuCode, orderId);

        if (processedOrderRepository.existsById(orderId)) {
            log.warn("Orden {} ya procesada anteriormente. Saltando para asegurar idempotencia.", orderId);
            return;
        }

        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new InventoryNotFoundException(skuCode));

        inventory.decreaseStock(quantity);
        inventoryRepository.save(inventory);

        processedOrderRepository.save(ProcessedOrderEntity.builder()
                .orderId(orderId)
                .processedAt(LocalDateTime.now())
                .build());

        log.info("Stock reducido y orden {} registrada con éxito.", orderId);
    }
}
