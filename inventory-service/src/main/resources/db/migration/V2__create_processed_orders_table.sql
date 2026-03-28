-- Script de migración para Inventory Service
-- Versión: 2
-- Descripción: Creación de la tabla processed_orders para idempotencia

CREATE TABLE processed_orders (
    order_id UUID PRIMARY KEY,
    processed_at TIMESTAMP NOT NULL
);

-- Comentario para documentación
COMMENT ON TABLE processed_orders IS 'Tabla de idempotencia para registrar las órdenes procesadas por el inventario';
COMMENT ON COLUMN processed_orders.order_id IS 'Identificador único de la orden procesada';
COMMENT ON COLUMN processed_orders.processed_at IS 'Fecha y hora en que se procesó la orden';
