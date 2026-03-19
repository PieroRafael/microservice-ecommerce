package com.cloudnative.ecommerce.order.infrastructure.rest.client;

import com.cloudnative.ecommerce.order.infrastructure.rest.client.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

/**
 * Cliente Feign declarativo para el microservicio de productos.
 * Spring Cloud Eureka se encarga de la implementación en tiempo de ejecución.
 */
@FeignClient(name = "product-service", path = "/api/v1/products")
public interface FeignProductClient {

    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable UUID id);
}
