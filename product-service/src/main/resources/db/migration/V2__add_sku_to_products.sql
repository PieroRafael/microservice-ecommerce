-- Migración para añadir el campo SKU a la tabla productos
-- Versión: 2
-- Descripción: Adición de columna sku con restricción de unicidad y not null

ALTER TABLE products ADD COLUMN sku VARCHAR(255);

-- Si existen datos previos, generamos un SKU temporal basado en el ID para no romper el NOT NULL
UPDATE products SET sku = 'TEMP-' || id WHERE sku IS NULL;

ALTER TABLE products ALTER COLUMN sku SET NOT NULL;
ALTER TABLE products ADD CONSTRAINT uk_products_sku UNIQUE (sku);
