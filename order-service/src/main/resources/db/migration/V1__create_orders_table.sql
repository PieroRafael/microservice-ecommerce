-- Script de migración inicial para Order Service
-- Versión: 1
-- Descripción: Creación de la tabla orders

CREATE TABLE orders (
    id UUID PRIMARY KEY,
    order_number VARCHAR(255) NOT NULL,
    sku_code VARCHAR(255) NOT NULL,
    price DECIMAL(19, 2) NOT NULL,
    quantity INTEGER NOT NULL
);
