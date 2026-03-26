/**
 * KafkaOrderEventPublisher - Infrastructure Adapter (Output).
 *
 * Implementación del puerto de salida OrderEventPublisher utilizando el ecosistema de Kafka.
 * Se encarga de transformar y enviar los eventos de dominio de órdenes al tópico correspondiente
 * para habilitar la comunicación asíncrona (EDA) con otros microservicios.
 */
package com.cloudnative.ecommerce.order.infrastructure.messaging;

import com.cloudnative.ecommerce.order.domain.event.OrderCreatedEvent;
import com.cloudnative.ecommerce.order.domain.port.out.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaOrderEventPublisher implements OrderEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "order-placed-topic";

    @Override
    public void publishOrderCreated(OrderCreatedEvent event) {
        log.info("Publicando evento OrderCreatedEvent para la orden: {} en el tópico: {}", event.orderId(), TOPIC);
        kafkaTemplate.send(TOPIC, event);
    }
}
