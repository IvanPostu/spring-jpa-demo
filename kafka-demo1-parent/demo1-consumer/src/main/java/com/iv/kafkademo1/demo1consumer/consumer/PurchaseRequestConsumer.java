package com.iv.kafkademo1.demo1consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.iv.kafkademo1.demo1common.entity.PurchaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

//@Service
public class PurchaseRequestConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseRequestConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("cachePurchaseRequest")
    private Cache<Integer, Boolean> cache;

    private boolean isExistsInCache(int purchaseRequestId) {
        return Optional.ofNullable(cache.getIfPresent(purchaseRequestId)).orElse(false);
    }

    @KafkaListener(topics = "t-purchase-request")
    public void consume(String message) throws JsonMappingException, JsonProcessingException {
        var purchaseRequest = objectMapper.readValue(message, PurchaseRequest.class);

        var processed = isExistsInCache(purchaseRequest.getId());

        if (processed) {
            LOG.info("Skipping {}, because iw was already processed", purchaseRequest);
            return;
        }

        LOG.info("Processing {}", purchaseRequest);
        cache.put(purchaseRequest.getId(), true);
    }

}
