package com.cloudnative.ecommerce.order.domain.exception;

/**
 * Excepcion lanzada cuando un servicio externo no esta disponible
 * temporalmente.
 * Se mapea a HTTP 503 Service Unavailable con Retry-After header.
 * Se utiliza por resiliencia (CircuitBreaker, RateLimiter, Bulkhead, Timeout).
 */
public class ServiceUnavailableException extends RuntimeException {

    private final String serviceName;

    public ServiceUnavailableException(String serviceName, String message) {
        super(message);
        this.serviceName = serviceName;
    }

    public ServiceUnavailableException(String serviceName, String message, Throwable cause) {
        super(message, cause);
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
