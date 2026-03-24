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
                .sku(request.sku())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .build();
    }

    public ProductResponse toResponse(Product domain) {
        if (domain == null)
            return null;
        return new ProductResponse(
                domain.getId(),
                domain.getSku(),
                domain.getName(),
                domain.getDescription(),
                domain.getPrice(),
                domain.hasStock());
    }
}
