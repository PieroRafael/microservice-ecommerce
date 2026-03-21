package com.cloudnative.ecommerce.order.infrastructure.rest.client;

import com.cloudnative.ecommerce.order.domain.port.out.ProductGateway;
import com.cloudnative.ecommerce.order.infrastructure.rest.client.dto.ProductResponse;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.annotation.PostConstruct;
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
    private final RetryRegistry retryRegistry;

    @PostConstruct
    public void setupLoggers() {
        retryRegistry.retry("productService").getEventPublisher()
                .onRetry(event -> log.warn("Resilience4j Retry: Intento {} para la operación {}. Causa: {}",
                        event.getNumberOfRetryAttempts(),
                        event.getName(),
                        event.getLastThrowable().getMessage()));
    }

    @Override
    @CircuitBreaker(name = "productService")
    @Retry(name = "productService", fallbackMethod = "fallbackExistsById")
    public boolean existsById(UUID id) {
        try {
            ProductResponse response = productClient.getProductById(id);
            return response != null;
        } catch (FeignException.NotFound e) {
            log.warn("Producto no encontrado en product-service: {}", id);
            return false;
        }
        // Las excepciones 5xx o de conexión se propagan para que CircuitBreaker las registre
    }

    public boolean fallbackExistsById(UUID id, Throwable t) {
        log.error("Circuit Breaker OPEN o Error en product-service para el id {}. Fallback activado. Causa: {}", id, t.getMessage());
        throw new RuntimeException("Catálogo de productos no disponible temporalmente", t);
    }

    @Override
    @CircuitBreaker(name = "productService")
    @Retry(name = "productService", fallbackMethod = "fallbackExistsBySku")
    public boolean existsBySku(String sku) {
        try {
            ProductResponse response = productClient.getProductBySku(sku);
            return response != null;
        } catch (FeignException.NotFound e) {
            log.warn("Producto no encontrado en product-service por sku: {}", sku);
            return false;
        }
    }

    public boolean fallbackExistsBySku(String sku, Throwable t) {
        log.error("Circuit Breaker OPEN o Error en product-service para el sku {}. Fallback activado. Causa: {}", sku, t.getMessage());
        throw new RuntimeException("Catálogo de productos no disponible temporalmente", t);
    }
}
