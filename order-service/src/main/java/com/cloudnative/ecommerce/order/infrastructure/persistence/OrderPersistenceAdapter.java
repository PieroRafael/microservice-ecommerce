package com.cloudnative.ecommerce.order.infrastructure.persistence;

import com.cloudnative.ecommerce.order.domain.model.Order;
import com.cloudnative.ecommerce.order.domain.repository.OrderRepository;
import com.cloudnative.ecommerce.order.infrastructure.persistence.mapper.OrderMapper;
import com.cloudnative.ecommerce.order.infrastructure.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// @Adaptador: Esta clase es la que une el Puerto del Dominio con la Tecnología JPA
@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderRepository {

    private final OrderJpaRepository jpaRepository;
    private final OrderMapper mapper;

    @Override
    public Order save(Order order) {
        var entity = mapper.toEntity(order);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain); // :: (method reference) es como escribir entity -> mapper.toDomain(entity)
    }

    @Override
    public List<Order> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList(); // o collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
