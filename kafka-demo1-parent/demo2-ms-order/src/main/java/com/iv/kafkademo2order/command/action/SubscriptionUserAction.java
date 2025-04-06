package com.iv.kafkademo2order.command.action;

import com.iv.kafkademo2common.broker.message.SubscriptionUserMessage;
import com.iv.kafkademo2order.api.request.SubscriptionUserRequest;
import com.iv.kafkademo2order.broker.producer.SubscriptionUserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SubscriptionUserAction {

    @Autowired
    private SubscriptionUserProducer producer;

    public void publishToKafka(SubscriptionUserRequest request) {
        var message = new SubscriptionUserMessage();

        message.setDuration(request.getDuration());
        message.setUsername(request.getUsername());

        producer.publish(message);
    }

}
