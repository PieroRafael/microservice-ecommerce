/**
 * ProcessedOrderEntity - Infrastructure Entity.
 * 
 * Esta entidad registra los Identificadores de Órdenes (orderId) que ya han sido
 * procesados por el servicio de inventario. Sirve como nuestra "Tabla de Idempotencia"
 * para evitar procesar el mismo evento de Kafka múltiples veces Y evitar duplicados y falsos positivos.
 */
package com.cloudnative.ecommerce.inventory.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "processed_orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedOrderEntity {

    @Id
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt;
}
