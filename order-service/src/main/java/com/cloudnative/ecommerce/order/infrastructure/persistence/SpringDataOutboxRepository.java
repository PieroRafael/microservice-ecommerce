/**
 * SpringDataOutboxRepository - Infrastructure Component.
 * 
 * Puente de Spring Data JPA para la persistencia de la tabla outbox_events.
 */
package com.cloudnative.ecommerce.order.infrastructure.persistence;

import com.cloudnative.ecommerce.order.domain.model.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SpringDataOutboxRepository extends JpaRepository<OutboxEntity, UUID> {
    List<OutboxEntity> findByStatusOrderByCreatedAtAsc(OutboxStatus status);
}

