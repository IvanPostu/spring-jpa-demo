package com.iv.kafkademo2order.command.action;

import com.iv.kafkademo2common.broker.message.PremiumPurchaseMessage;
import com.iv.kafkademo2order.api.request.PremiumPurchaseRequest;
import com.iv.kafkademo2order.broker.producer.PremiumPurchaseProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PremiumPurchaseAction {

    @Autowired
    private PremiumPurchaseProducer producer;

    public void publishToKafka(PremiumPurchaseRequest request) {
        var message = new PremiumPurchaseMessage();

        message.setUsername(request.getUsername());
        message.setItem(request.getItem());
        message.setPurchaseNumber(request.getPurchaseNumber());

        producer.publish(message);
    }

}

