package com.cloudnative.ecommerce.product.infrastructure.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.cloudnative.ecommerce.product.application.service.ProductService;
import com.cloudnative.ecommerce.product.infrastructure.rest.mapper.ProductRestMapper;

import lombok.RequiredArgsConstructor;

import com.cloudnative.ecommerce.product.infrastructure.rest.dto.ProductRequest;
import com.cloudnative.ecommerce.product.infrastructure.rest.dto.ProductResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRestMapper restMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        var domain = restMapper.toDomain(request);
        var savedDomain = productService.createProduct(domain);
        return restMapper.toResponse(savedDomain);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable UUID id) {
        return restMapper.toResponse(productService.getProductById(id));
    }

    @GetMapping("/sku/{sku}")
    public ProductResponse getProductBySku(@PathVariable String sku) {
        return restMapper.toResponse(productService.getProductBySku(sku));
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(restMapper::toResponse)
                .toList();
    }

    @GetMapping("/status")
    public Map<String, String> getStatus() {
        return Map.of(
                "status", "UP",
                "message", "Product Service is running with Virtual Threads!",
                "thread", Thread.currentThread().toString());
    }
}
