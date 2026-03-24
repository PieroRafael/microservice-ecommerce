-- Script de migración inicial para Inventory Service
-- Versión: 1
-- Descripción: Creación de la tabla inventory

CREATE TABLE inventory (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sku_code VARCHAR(255) NOT NULL UNIQUE,
    quantity INTEGER NOT NULL DEFAULT 0,
    reserved INTEGER NOT NULL DEFAULT 0,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índice para búsquedas rápidas por SKU
CREATE INDEX idx_inventory_sku_code ON inventory(sku_code);
