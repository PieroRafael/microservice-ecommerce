/**
 * KafkaConsumerConfig - Infrastructure Configuration.
 * 
 * Configura el ecosistema de consumo de Kafka para el microservicio.
 * Habilita el soporte para JSON Deserialization y asegura que las clases
 * de eventos sean confiables a través de TRUSTED_PACKAGES.
 */
package com.cloudnative.ecommerce.inventory.infrastructure.messaging;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        
        // Permitir deserialización de records de dominio
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.cloudnative.ecommerce.*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        
        // Mapeo explícito (opcional si se usa el mismo nombre de clase, pero recomendado)
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.cloudnative.ecommerce.inventory.domain.event.OrderCreatedEvent");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = 
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // El soporte para Virtual Threads se activa vía application.yml o propiedad global
        return factory;
    }
}
