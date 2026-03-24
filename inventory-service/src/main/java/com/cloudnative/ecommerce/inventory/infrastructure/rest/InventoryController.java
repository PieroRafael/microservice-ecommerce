package com.cloudnative.ecommerce.inventory.infrastructure.rest;

import com.cloudnative.ecommerce.inventory.application.service.InventoryService;
import com.cloudnative.ecommerce.inventory.infrastructure.rest.dto.InventoryRequest;
import com.cloudnative.ecommerce.inventory.infrastructure.rest.dto.InventoryResponse;
import com.cloudnative.ecommerce.inventory.infrastructure.rest.mapper.InventoryRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryRestMapper restMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createInventory(@Valid @RequestBody InventoryRequest request) {
        var domain = restMapper.toDomain(request);
        var saved = inventoryService.createInventory(domain);
        return restMapper.toResponse(saved);
    }

    @GetMapping("/{skuCode}")
    public InventoryResponse getBySkuCode(@PathVariable String skuCode) {
        return restMapper.toResponse(inventoryService.getBySkuCode(skuCode));
    }

    @GetMapping
    public List<InventoryResponse> getAll() {
        return inventoryService.getAll().stream()
                .map(restMapper::toResponse)
                .toList();
    }

    @GetMapping("/{skuCode}/in-stock")
    public boolean isInStock(@PathVariable String skuCode,
            @RequestParam(defaultValue = "1") int quantity) {
        return inventoryService.isInStock(skuCode, quantity);
    }
}
