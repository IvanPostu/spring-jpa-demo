package com.iv.kafkademo1.demo1consumer.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.iv.kafkademo1.demo1common.entity.CarLocation;
import com.iv.kafkademo1.demo1common.entity.PurchaseRequest2;
import com.iv.kafkademo1.demo1common.util.Hash;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Optional;

@Configuration
public class KafkaConfig {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaConfig.class);

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("cachePurchaseRequest2")
    private Cache<String, Boolean> cachePurchaseRequest2;

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        var properties = kafkaProperties.buildConsumerProperties();

        properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "5000");
        properties.put(ConsumerConfig.METRICS_SAMPLE_WINDOW_MS_CONFIG, "40000");

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean(name = "farLocationContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> farLocationContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        configurer.configure(factory, consumerFactory());

        factory.setRecordFilterStrategy(new RecordFilterStrategy<Object, Object>() {

            @Override
            public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
                try {
                    CarLocation carLocation = objectMapper.readValue(consumerRecord.value().toString(),
                            CarLocation.class);
                    return carLocation.getDistance() <= 100;
                } catch (JsonProcessingException e) {
                    return false;
                }
            }
        });

        return factory;
    }

    @Bean(name = "purchaseContainerRequestFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> purchaseContainerRequestFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        configurer.configure(factory, consumerFactory());

        factory.setRecordFilterStrategy(new RecordFilterStrategy<Object, Object>() {
            @Override
            public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
                try {
                    PurchaseRequest2 purchaseRequest = objectMapper.readValue(consumerRecord.value().toString(),
                            PurchaseRequest2.class);
                    String idempotencyKey = Hash.calculateSha256(""
                            + purchaseRequest.getPrNumber() + ";" + purchaseRequest.getAmount()
                            + ";" + purchaseRequest.getPrNumber());
                    boolean isCached = Optional
                            .ofNullable(cachePurchaseRequest2.getIfPresent(idempotencyKey)).orElse(false);

                    if (isCached) {
                        LOG.info("Skipped: {}", purchaseRequest);
                        return true;
                    }

                    return false;
                } catch (JsonProcessingException e) {
                    return false;
                }
            }
        });

        return factory;
    }

    @Bean(name = "imageRetryContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> imageRetryContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        configurer.configure(factory, consumerFactory());

        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(10_000, 3)));

        return factory;
    }


}
