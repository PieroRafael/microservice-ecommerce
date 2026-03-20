package com.cloudnative.ecommerce.order.infrastructure.rest.client;

import com.cloudnative.ecommerce.order.domain.port.out.ProductGateway;
import com.cloudnative.ecommerce.order.infrastructure.rest.client.dto.ProductResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Adaptador de Infraestructura que implementa el Puerto de Salida.
 * Aísla al dominio de la tecnología específica (Feign).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductFeignAdapter implements ProductGateway {

    private final FeignProductClient productClient;

    @Override
    public boolean existsById(UUID id) {
        try {
            ProductResponse response = productClient.getProductById(id);
            return response != null;
        } catch (FeignException.NotFound e) {
            log.warn("Producto no encontrado en product-service: {}", id);
            return false;
        } catch (Exception e) {
            log.error("Error al consultar product-service para el id: {}", id, e);
            throw new RuntimeException("Error en comunicación con catálogo de productos", e);
        }
    }

    @Override
    public boolean existsBySku(String sku) {
        try {
            ProductResponse response = productClient.getProductBySku(sku);
            return response != null;
        } catch (FeignException.NotFound e) {
            log.warn("Producto no encontrado en product-service por sku: {}", sku);
            return false;
        } catch (Exception e) {
            log.error("Error al consultar product-service para el sku: {}", sku, e);
            throw new RuntimeException("Error en comunicación con catálogo de productos", e);
        }
    }
}
