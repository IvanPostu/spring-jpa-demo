package com.iv.kafkademo2order.broker.producer;


import com.iv.kafkademo2common.broker.message.InventoryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryProducer {

    @Autowired
    private KafkaTemplate<String, InventoryMessage> kafkaTemplate;

    public void publish(InventoryMessage message) {
        kafkaTemplate.send("t-commodity-inventory", message.getItem(), message);
    }

}

