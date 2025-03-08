package com.iv.kafkademo1.demo1consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.iv.kafkademo1.demo1consumer.entity.PurchaseRequest2;
import com.iv.kafkademo1.demo1consumer.util.Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PurchaseRequestConsumer2 {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseRequestConsumer2.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("cachePurchaseRequest2")
    private Cache<String, Boolean> cachePurchaseRequest2;

    @KafkaListener(topics = "t-purchase-request2", containerFactory = "purchaseContainerRequestFactory")
    public void consume(String message) throws JsonMappingException, JsonProcessingException {
        PurchaseRequest2 purchaseRequest = objectMapper.readValue(message, PurchaseRequest2.class);

        LOG.info("Processing {}", purchaseRequest);

        String idempotencyKey = Hash.calculateSha256(""
                + purchaseRequest.getPrNumber() + ";" + purchaseRequest.getAmount()
                + ";" + purchaseRequest.getPrNumber());
        cachePurchaseRequest2.put(idempotencyKey, true);
    }


}
