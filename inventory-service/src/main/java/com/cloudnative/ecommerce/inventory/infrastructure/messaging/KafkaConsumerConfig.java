/**
 * KafkaConsumerConfig - Infrastructure Configuration.
 * 
 * Configura el ecosistema de consumo de Kafka para el microservicio.
 * Habilita el soporte para JSON Deserialization y asegura que las clases
 * de eventos sean confiables a través de TRUSTED_PACKAGES.
 */
package com.cloudnative.ecommerce.inventory.infrastructure.messaging;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
        // 🛠️ CONFIGURACIÓN FINAL ROBUSTA (ESTÁNDAR 2026)
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Instanciamos el deserializador para el tipo específico (usando casting para compatibilidad de Bean)
        @SuppressWarnings("unchecked")
        JsonDeserializer<Object> jsonDeserializer = (JsonDeserializer) new JsonDeserializer<>(
                com.cloudnative.ecommerce.inventory.domain.event.OrderCreatedEvent.class, 
                objectMapper
        );
        
        jsonDeserializer.addTrustedPackages("com.cloudnative.ecommerce.*");
        jsonDeserializer.setUseTypeHeaders(false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        
        return new DefaultKafkaConsumerFactory<>(
                props, 
                new StringDeserializer(), 
                jsonDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = 
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
