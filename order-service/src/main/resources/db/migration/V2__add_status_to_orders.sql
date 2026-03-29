-- V2__add_status_to_orders.sql
-- Agrega la columna status para gestionar el patrón Saga

ALTER TABLE orders ADD COLUMN status VARCHAR(50) NOT NULL DEFAULT 'PENDING_INVENTORY';
