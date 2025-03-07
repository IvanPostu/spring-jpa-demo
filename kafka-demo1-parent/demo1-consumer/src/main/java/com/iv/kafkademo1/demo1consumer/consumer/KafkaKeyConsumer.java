package com.iv.kafkademo1.demo1consumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class KafkaKeyConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaKeyConsumer.class);

    @KafkaListener(groupId = "default-spring-consumer", concurrency = "4", topicPartitions = {
            @TopicPartition(topic = "t-multi-partitions", partitions = {"0", "1", "2", "3"})
    })
    public void consume(ConsumerRecord<String, String> consumerRecord) throws InterruptedException {
        LOG.info("Key : {}, Partition : {}, Message : {}", consumerRecord.key(), consumerRecord.partition(),
                consumerRecord.value());
        TimeUnit.SECONDS.sleep(1);
    }

}
