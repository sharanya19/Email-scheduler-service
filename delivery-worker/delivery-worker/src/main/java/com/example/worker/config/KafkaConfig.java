package com.example.worker.config;

import com.example.worker.model.Email;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, Email> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9093");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Custom module to register serializers for LocalDateTime and EmailStatus
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) {
                try {
                    gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // ISO format
                } catch (IOException e) {
                    throw new RuntimeException("Error serializing LocalDateTime", e);
                }
            }
        });
        module.addSerializer(Email.EmailStatus.class, new JsonSerializer<Email.EmailStatus>() {
            @Override
            public void serialize(Email.EmailStatus value, JsonGenerator gen, SerializerProvider serializers) {
                try {
                    gen.writeString(value.name()); // Serialize as string
                } catch (IOException e) {
                    throw new RuntimeException("Error serializing EmailStatus", e);
                }
            }
        });

        // Add module to the object mapper
        config.put("spring.kafka.producer.value-serializer", module);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Email> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
