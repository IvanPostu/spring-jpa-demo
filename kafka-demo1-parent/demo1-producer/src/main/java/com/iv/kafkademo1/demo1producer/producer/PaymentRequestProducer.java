package com.iv.kafkademo1.demo1producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iv.kafkademo1.demo1producer.entity.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentRequestProducer {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentRequestProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(PaymentRequest paymentRequest) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(paymentRequest);
        kafkaTemplate.send("t-payment-request", paymentRequest.getPaymentNumber(), json);
        LOG.info("Sent : {}", paymentRequest);
    }

}
