package com.iv.kafkademo1.demo1consumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class FixedRateConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(FixedRateConsumer.class);

    @KafkaListener(topics = "t-fixedrate")
    public void consume(String message) {
        LOG.info("Consuming : {}", message);
    }

}
