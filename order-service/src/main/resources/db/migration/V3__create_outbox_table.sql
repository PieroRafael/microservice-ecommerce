-- 
-- V3__create_outbox_table.sql - Database Migration
-- 
-- Esta tabla implementa el patrón Transactional Outbox para garantizar la 
-- consistencia atómica entre la DB y Kafka.
-- 

CREATE TABLE outbox_events (
    id UUID PRIMARY KEY,
    aggregate_id UUID NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    payload JSONB NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP WITHOUT TIME ZONE
);

-- Índices para optimizar el Polling Job masivo
CREATE INDEX idx_outbox_status_created ON outbox_events (status, created_at) WHERE status = 'PENDING';
