package com.cloudnative.ecommerce.order.infrastructure.rest.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class OrderRequest {

    @NotBlank(message = "El número de orden es requerido")
    private String orderNumber;

    @NotBlank(message = "El código SKU es requerido")
    private String skuCode;

    @NotNull(message = "El precio es requerido")
    @PositiveOrZero(message = "El precio debe ser mayor o igual a cero")
    private BigDecimal price;

    @NotNull(message = "La cantidad es requerida")
    @PositiveOrZero(message = "La cantidad debe ser mayor o igual a cero")
    private Integer quantity;

}
