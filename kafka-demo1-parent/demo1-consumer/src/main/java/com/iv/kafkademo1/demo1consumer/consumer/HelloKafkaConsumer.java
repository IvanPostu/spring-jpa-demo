package com.iv.kafkademo1.demo1consumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class HelloKafkaConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(HelloKafkaConsumer.class);

    @KafkaListener(topics = "t-hello")
    public void consume(String message) {
        LOG.info("Consumed message: {}", message);
    }

}
