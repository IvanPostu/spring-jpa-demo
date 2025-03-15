package com.iv.demo2.kafka.stream.serde;

import com.iv.kafkademo2common.broker.message.PromotionMessage;

public class PromotionSerde extends CustomJsonSerde<PromotionMessage> {

    public PromotionSerde() {
        super(new CustomJsonSerializer<PromotionMessage>(),
                new CustomJsonDeserializer<PromotionMessage>(PromotionMessage.class));
    }

}
