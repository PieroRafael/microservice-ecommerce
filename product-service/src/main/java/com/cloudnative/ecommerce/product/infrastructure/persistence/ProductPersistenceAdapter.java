package com.cloudnative.ecommerce.product.infrastructure.persistence;

import com.cloudnative.ecommerce.product.domain.model.Product;
import com.cloudnative.ecommerce.product.domain.repository.ProductRepository;
import com.cloudnative.ecommerce.product.infrastructure.persistence.mapper.ProductMapper;
import com.cloudnative.ecommerce.product.infrastructure.persistence.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// @Adaptador: Esta clase es la que une el Puerto del Dominio con la Tecnología JPA
@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepository {

    private final ProductJpaRepository jpaRepository;
    private final ProductMapper mapper;

    @Override
    public Product save(Product product) {
        var entity = mapper.toEntity(product);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain); // :: (method reference) es como escribir entity -> mapper.toDomain(entity)
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return jpaRepository.findBySku(sku)
                .map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList(); // o collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
