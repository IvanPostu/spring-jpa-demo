package com.iv.kafkademo1.demo1consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iv.kafkademo1.demo1common.entity.Commodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class CommodityNotificationConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(CommodityNotificationConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "t-commodity", groupId = "cg-notification")
    public void consume(String message) throws JsonMappingException, JsonProcessingException {
        var commodity = objectMapper.readValue(message, Commodity.class);
        LOG.info("Notification logic for : {}", commodity);
    }

}
