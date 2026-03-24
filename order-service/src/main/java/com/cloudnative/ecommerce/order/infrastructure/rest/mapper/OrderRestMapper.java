package com.cloudnative.ecommerce.order.infrastructure.rest.mapper;
 
import com.cloudnative.ecommerce.order.domain.model.Order;
import com.cloudnative.ecommerce.order.infrastructure.rest.dto.OrderRequest;
import com.cloudnative.ecommerce.order.infrastructure.rest.dto.OrderResponse;
import org.springframework.stereotype.Component;
 
@Component
public class OrderRestMapper {
 
    public Order toDomain(OrderRequest request) {
        if (request == null)
            return null;
        return Order.builder()
                .orderNumber(request.orderNumber())
                .skuCode(request.skuCode())
                .price(request.price())
                .quantity(request.quantity())
                .build();
    }
 
    public OrderResponse toResponse(Order domain) {
        if (domain == null)
            return null;
        return new OrderResponse(
                domain.getId(),
                domain.getOrderNumber(),
                domain.getSkuCode(),
                domain.getQuantity(),
                domain.getPrice(),
                domain.hasQuantity());
    }
}
