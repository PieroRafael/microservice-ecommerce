-- Script de migración inicial para Product Service
-- Versión: 1
-- Descripción: Creación de la tabla products

CREATE TABLE products (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(19, 2) NOT NULL,
    stock INTEGER NOT NULL
);
