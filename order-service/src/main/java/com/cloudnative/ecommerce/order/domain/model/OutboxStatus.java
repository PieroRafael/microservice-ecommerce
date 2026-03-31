/**
 * OutboxStatus - Domain Model (Enum).
 * 
 * Define los estados posibles para el ciclo de vida de un evento en el outbox.
 */
package com.cloudnative.ecommerce.order.domain.model;

/**
 * OutboxStatus - Domain Model.
 * 
 * Define los estados posibles de un evento en el Transactional Outbox.
 */
public enum OutboxStatus {
    PENDING,
    SENT,
    FAILED
}
