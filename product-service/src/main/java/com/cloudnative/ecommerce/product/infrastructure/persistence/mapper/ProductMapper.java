package com.cloudnative.ecommerce.product.infrastructure.persistence.mapper;

import com.cloudnative.ecommerce.product.domain.model.Product;
import com.cloudnative.ecommerce.product.infrastructure.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(ProductEntity entity) {
        if (entity == null)
            return null;
        return Product.builder()
                .id(entity.getId())
                .sku(entity.getSku())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .build();
    }

    public ProductEntity toEntity(Product domain) {
        if (domain == null)
            return null;
        return ProductEntity.builder()
                .id(domain.getId())
                .sku(domain.getSku())
                .name(domain.getName())
                .description(domain.getDescription())
                .price(domain.getPrice())
                .stock(domain.getStock())
                .build();
    }
}
