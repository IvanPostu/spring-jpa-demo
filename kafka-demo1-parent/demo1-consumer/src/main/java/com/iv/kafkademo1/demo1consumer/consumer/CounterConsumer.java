package com.iv.kafkademo1.demo1consumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//@Service
public class CounterConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(CounterConsumer.class);

    @KafkaListener(topics = "t-counter", groupId = "counter-group-fast")
    public void consumeFast(String message) {
        LOG.info("Fast: {}", message);
    }

    @KafkaListener(topics = "t-counter", groupId = "counter-group-slow")
    public void consumeSlow(String message) throws Exception {
        TimeUnit.SECONDS.sleep(3);
        LOG.info("Slow: {}", message);
    }

}
