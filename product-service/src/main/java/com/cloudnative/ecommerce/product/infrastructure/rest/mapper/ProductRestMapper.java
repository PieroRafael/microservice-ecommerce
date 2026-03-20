package com.cloudnative.ecommerce.product.infrastructure.rest.mapper;

import com.cloudnative.ecommerce.product.domain.model.Product;
import com.cloudnative.ecommerce.product.infrastructure.rest.dto.ProductRequest;
import com.cloudnative.ecommerce.product.infrastructure.rest.dto.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductRestMapper {

    public Product toDomain(ProductRequest request) {
        if (request == null)
            return null;
        return Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }

    public ProductResponse toResponse(Product domain) {
        if (domain == null)
            return null;
        return ProductResponse.builder()
                .id(domain.getId())
                .sku(domain.getSku())
                .name(domain.getName())
                .description(domain.getDescription())
                .price(domain.getPrice())
                .available(domain.hasStock())
                .build();
    }
}
