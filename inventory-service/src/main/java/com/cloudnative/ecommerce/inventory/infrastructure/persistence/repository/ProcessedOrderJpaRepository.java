/**
 * ProcessedOrderJpaRepository - Infrastructure Repository.
 * 
 * Repositorio JPA para la gestión de registros de idempotencia.
 * Permite verificar rápidamente si una orden ya fue procesada antes de ejecutar
 * la lógica de negocio.
 */
package com.cloudnative.ecommerce.inventory.infrastructure.persistence.repository;

import com.cloudnative.ecommerce.inventory.infrastructure.persistence.entity.ProcessedOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProcessedOrderJpaRepository extends JpaRepository<ProcessedOrderEntity, UUID> {
}
