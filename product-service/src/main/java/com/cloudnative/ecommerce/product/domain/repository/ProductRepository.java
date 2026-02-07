package com.cloudnative.ecommerce.product.domain.repository;

import com.cloudnative.ecommerce.product.domain.model.Product;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

// @Puerto: Esta interfaz define el contrato de operaciones que el Dominio requiere y que la Infraestructura debe implementar.
public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(UUID id);

    List<Product> findAll();

    void deleteById(UUID id);
}
