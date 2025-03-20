package com.iv.kafkademo2order.command.action;

import com.iv.kafkademo2common.broker.message.FlashSaleVoteMessage;
import com.iv.kafkademo2order.api.request.FlashSaleVoteRequest;
import com.iv.kafkademo2order.broker.producer.FlashSaleVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlashSaleVoteAction {

    @Autowired
    private FlashSaleVoteProducer producer;

    public void publishToKafka(FlashSaleVoteRequest request) {
        var message = new FlashSaleVoteMessage();

        message.setCustomerId(request.getCustomerId());
        message.setItemName(request.getItemName());

        producer.publish(message);
    }

}
