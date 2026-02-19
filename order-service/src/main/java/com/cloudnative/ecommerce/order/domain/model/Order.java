package com.cloudnative.ecommerce.order.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private UUID id;
    private String orderNumber;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

    // Lógica de validación de dominio
    public boolean hasQuantity() {
        return quantity != null && quantity > 0;
    }

}
