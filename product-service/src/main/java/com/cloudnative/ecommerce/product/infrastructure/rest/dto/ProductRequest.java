package com.cloudnative.ecommerce.product.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "El SKU es obligatorio") String sku,

        @NotBlank(message = "El nombre es obligatorio") String name,

        String description,

        @NotNull(message = "El precio es obligatorio") @PositiveOrZero(message = "El precio debe ser mayor o igual a cero") BigDecimal price,

        @NotNull(message = "El stock es obligatorio") @PositiveOrZero(message = "El stock no puede ser negativo") Integer stock) {
}
