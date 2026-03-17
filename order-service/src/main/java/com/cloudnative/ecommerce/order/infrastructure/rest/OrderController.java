package com.cloudnative.ecommerce.order.infrastructure.rest;

import com.cloudnative.ecommerce.order.application.service.OrderService;
import com.cloudnative.ecommerce.order.infrastructure.rest.dto.OrderRequest;
import com.cloudnative.ecommerce.order.infrastructure.rest.dto.OrderResponse;
import com.cloudnative.ecommerce.order.infrastructure.rest.mapper.OrderRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRestMapper restMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request) {
        var domain = restMapper.toDomain(request);
        var savedDomain = orderService.createOrder(domain);
        return restMapper.toResponse(savedDomain);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable UUID id) {
        return restMapper.toResponse(orderService.getOrderById(id));
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders().stream()
                .map(restMapper::toResponse)
                .toList();
    }

    @GetMapping("/status")
    public Map<String, String> getStatus() {
        return Map.of(
                "status", "UP",
                "message", "Order Service is running with Virtual Threads!",
                "thread", Thread.currentThread().toString());
    }
}
