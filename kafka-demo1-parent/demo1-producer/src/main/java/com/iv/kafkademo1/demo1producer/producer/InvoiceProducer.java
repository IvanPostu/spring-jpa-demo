package com.iv.kafkademo1.demo1producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iv.kafkademo1.demo1common.entity.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InvoiceProducer {
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(Invoice invoice) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(invoice);
        kafkaTemplate.send("t-invoice", invoice.getAmount() % 2, invoice.getInvoiceNumber(), json);

        LOG.info("Produced: {}", invoice);
    }

}
