package com.cloudnative.ecommerce.order.domain.model;

/**
 * Representa los estados posibles de una Orden dentro de la Coreografía Saga.
 */
public enum OrderStatus {
    PENDING_INVENTORY, // ⏳ Estado inicial: Esperando reserva de stock
    COMPLETED,         // ✅ Stock reservado con éxito (Happy Path)
    CANCELLED          // ❌ Stock insuficiente (Compensating Transaction)
}
