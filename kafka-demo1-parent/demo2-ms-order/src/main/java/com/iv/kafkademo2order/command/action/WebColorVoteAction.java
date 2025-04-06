package com.iv.kafkademo2order.command.action;

import com.iv.kafkademo2common.broker.message.WebColorVoteMessage;
import com.iv.kafkademo2order.api.request.WebColorVoteRequest;
import com.iv.kafkademo2order.broker.producer.WebColorVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebColorVoteAction {

    @Autowired
    private WebColorVoteProducer producer;

    public void publishToKafka(WebColorVoteRequest request) {
        var message = new WebColorVoteMessage();

        message.setUsername(request.getUsername());
        message.setColor(request.getColor());
        message.setVoteDateTime(request.getVoteDateTime());

        producer.publish(message);
    }

}

