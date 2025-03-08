package com.iv.kafkademo1.demo1producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iv.kafkademo1.demo1common.entity.PurchaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PurchaseRequestProducer {
    private static final Logger LOG = LoggerFactory.getLogger(PurchaseRequestProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(PurchaseRequest purchaseRequest) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(purchaseRequest);
        kafkaTemplate.send("t-purchase-request", purchaseRequest.getPrNumber(), json);
        LOG.info("Sent : {}", purchaseRequest);
    }

}
