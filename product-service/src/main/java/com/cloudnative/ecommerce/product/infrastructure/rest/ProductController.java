package com.cloudnative.ecommerce.product.infrastructure.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping("/status")
    public Map<String, String> getStatus() {
        return Map.of(
                "status", "UP",
                "message", "Product Service is running with Virtual Threads!",
                "thread", Thread.currentThread().toString());
    }
}
