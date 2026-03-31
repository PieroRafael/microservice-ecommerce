/**
 * OutboxPersistenceAdapter - Infrastructure Adapter (Persistence).
 * 
 * Implementación del puerto OutboxRepository utilizando Spring Data JPA.
 */
package com.cloudnative.ecommerce.order.infrastructure.persistence;

import com.cloudnative.ecommerce.order.domain.model.OutboxEvent;
import com.cloudnative.ecommerce.order.domain.model.OutboxStatus;
import com.cloudnative.ecommerce.order.domain.port.out.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OutboxPersistenceAdapter implements OutboxRepository {

    private final SpringDataOutboxRepository springDataOutboxRepository;

    @Override
    public OutboxEvent save(OutboxEvent event) {
        OutboxEntity entity = OutboxEntity.builder()
                .id(event.id())
                .aggregateId(event.aggregateId())
                .eventType(event.eventType())
                .payload(event.payload())
                .status(event.status())
                .createdAt(event.createdAt())
                .processedAt(event.processedAt())
                .build();
        OutboxEntity saved = springDataOutboxRepository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public List<OutboxEvent> findByStatus(OutboxStatus status) {
        return springDataOutboxRepository.findByStatusOrderByCreatedAtAsc(status)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OutboxEvent> findById(UUID id) {
        return springDataOutboxRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public void delete(UUID id) {
        springDataOutboxRepository.deleteById(id);
    }

    private OutboxEvent mapToDomain(OutboxEntity entity) {
        return new OutboxEvent(
                entity.getId(),
                entity.getAggregateId(),
                entity.getEventType(),
                entity.getPayload(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getProcessedAt()
        );
    }
}

