package com.cloudnative.ecommerce.product.application.service;

import com.cloudnative.ecommerce.product.domain.model.Product;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product createProduct(Product product);

    Product getProductById(UUID id);

    Product getProductBySku(String sku);

    List<Product> getAllProducts();

    void deleteProduct(UUID id);
}
