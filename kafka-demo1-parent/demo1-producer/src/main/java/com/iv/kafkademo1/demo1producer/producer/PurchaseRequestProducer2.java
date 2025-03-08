package com.iv.kafkademo1.demo1producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iv.kafkademo1.demo1common.entity.PurchaseRequest2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PurchaseRequestProducer2 {
    private static final Logger LOG = LoggerFactory.getLogger(PurchaseRequestProducer2.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(PurchaseRequest2 purchaseRequest) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(purchaseRequest);
        kafkaTemplate.send("t-purchase-request2", json);
        LOG.info("Sent : {}", purchaseRequest);
    }

}
