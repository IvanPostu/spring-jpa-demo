package com.iv.kafkademo2order.broker.producer;

import com.iv.kafkademo2common.broker.message.FlashSaleVoteMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FlashSaleVoteProducer {

    @Autowired
    private KafkaTemplate<String, FlashSaleVoteMessage> kafkaTemplate;

    public void publish(FlashSaleVoteMessage message) {
        kafkaTemplate.send("t-commodity-flashsale-vote", message);
    }

}
