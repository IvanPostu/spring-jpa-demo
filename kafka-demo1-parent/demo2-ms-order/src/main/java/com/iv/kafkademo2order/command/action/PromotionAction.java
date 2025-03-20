package com.iv.kafkademo2order.command.action;

import com.iv.kafkademo2common.broker.message.PromotionMessage;
import com.iv.kafkademo2order.api.request.PromotionRequest;
import com.iv.kafkademo2order.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionAction {

    @Autowired
    private PromotionProducer producer;

    public void publishToKafka(PromotionRequest request) {
        var message = new PromotionMessage(request.getPromotionCode());

        producer.publish(message);
    }

}
