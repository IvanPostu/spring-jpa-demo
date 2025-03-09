package com.iv.kafkademo2order.command.action;

import com.iv.kafkademo2order.api.request.DiscountRequest;
import com.iv.kafkademo2order.broker.message.DiscountMessage;
import com.iv.kafkademo2order.producer.DiscountProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscountAction {

    @Autowired
    private DiscountProducer producer;

    public void publishToKafka(DiscountRequest request) {
        var message = new DiscountMessage(request.getDiscountCode(), request.getDiscountPercentage());
        producer.publish(message);
    }

}
