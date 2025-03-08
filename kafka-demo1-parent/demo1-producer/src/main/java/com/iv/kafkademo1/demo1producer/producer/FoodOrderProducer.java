package com.iv.kafkademo1.demo1producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iv.kafkademo1.demo1common.entity.FoodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderProducer {
    private static final Logger LOG = LoggerFactory.getLogger(FoodOrderProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(FoodOrder foodOrder) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(foodOrder);

        kafkaTemplate.send("t-food-order", json);
        LOG.info("Sent: {}", foodOrder);
    }

}
