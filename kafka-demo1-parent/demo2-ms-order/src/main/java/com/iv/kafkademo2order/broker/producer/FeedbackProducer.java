package com.iv.kafkademo2order.broker.producer;

import com.iv.kafkademo2common.broker.message.FeedbackMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FeedbackProducer {

    @Autowired
    private KafkaTemplate<String, FeedbackMessage> kafkaTemplate;

    public void publish(FeedbackMessage message) {
        kafkaTemplate.send("t-commodity-feedback", message);
    }

}
