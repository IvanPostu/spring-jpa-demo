package com.iv.kafkademo1.demo1producer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaKeyProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t-multi-partitions --offset earliest --partition 0
    // kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t-multi-partitions --offset earliest --partition 1
    // kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t-multi-partitions --offset earliest --partition 2
    public void send(String key, String value) {
        kafkaTemplate.send("t-multi-partitions", key, value);
    }

}
