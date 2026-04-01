package com.cloudnative.ecommerce.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * OrderApplication - Microservice Main Application Class.
 *
 * Clase principal del microservicio de órdenes que habilita el escaneo de
 * clientes Feign y la programación de tareas para el Outbox Relay basado en el
 * OUTBOX PATTERN.
 */
@SpringBootApplication
@EnableScheduling // Habilitamos la ejecución programada para el Outbox Relay
@EnableFeignClients
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
