package com.iv.kafkademo1.demo1producer.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    ProducerFactory<String, String> producerFactory(SslBundles sslBundles) {
        var properties = kafkaProperties.buildProducerProperties(sslBundles);
        properties.put(ProducerConfig.METRICS_SAMPLE_WINDOW_MS_CONFIG, "50000");
        properties.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, "5000");
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    KafkaTemplate<String, String> kafkaTemplate(SslBundles sslBundles) {
        return new KafkaTemplate<>(producerFactory(sslBundles));
    }

}
