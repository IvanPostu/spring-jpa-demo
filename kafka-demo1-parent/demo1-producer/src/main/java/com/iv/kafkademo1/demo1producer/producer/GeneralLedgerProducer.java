package com.iv.kafkademo1.demo1producer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GeneralLedgerProducer {
    private static final Logger LOG = LoggerFactory.getLogger(GeneralLedgerProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private AtomicInteger counter = new AtomicInteger();

    public void send(String message) {
        kafkaTemplate.send("t-general-ledger", message);
    }

    @Scheduled(fixedRate = 1000)
    public void schedule() {
        var message = "Ledger " + counter.incrementAndGet();
        send(message);

        LOG.info("Produced: {}", message);
    }

}
