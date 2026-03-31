/**
 * OutboxEntity - Infrastructure Persistence (JPA).
 * 
 * Mapeo físico de la tabla outbox_events para la persistencia transaccional.
 */
package com.cloudnative.ecommerce.order.infrastructure.persistence;

import com.cloudnative.ecommerce.order.domain.model.OutboxStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox_events")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutboxEntity {
    @Id
    private UUID id;
    
    @Column(name = "aggregate_id", nullable = false)
    private UUID aggregateId;
    
    @Column(name = "event_type", nullable = false)
    private String eventType;
    
    @Column(name = "payload", nullable = false, columnDefinition = "JSONB")
    private String payload;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutboxStatus status;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "processed_at")
    private LocalDateTime processedAt;
}

