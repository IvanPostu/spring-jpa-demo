package com.iv.kafkademo1.demo1consumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//@Service
public class RebalanceConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(RebalanceConsumer.class);

    // see "partitions assigned" log
    // e.g. group-rebalance: partitions assigned: [t-rebalance-beta-1]

    // see "rebalancing" log after creating new consumers or new partitions
    // e.g. [Consumer clientId=consumer-group-rebalance-3, groupId=group-rebalance] Request joining group due to: group is already rebalancing
    @KafkaListener(topics = {"t-rebalance-alpha", "t-rebalance-beta"}, groupId = "group-rebalance", concurrency = "3")
    public void consume(ConsumerRecord<String, String> consumerRecord) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        LOG.info("Partition : {}, Offset : {}, Message : {}", consumerRecord.partition(), consumerRecord.offset(),
                consumerRecord.value());
    }

}
