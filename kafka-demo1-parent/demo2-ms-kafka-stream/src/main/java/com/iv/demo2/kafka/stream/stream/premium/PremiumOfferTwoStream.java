package com.iv.demo2.kafka.stream.stream.premium;


import com.iv.kafkademo2common.broker.message.PremiumOfferMessage;
import com.iv.kafkademo2common.broker.message.PremiumPurchaseMessage;
import com.iv.kafkademo2common.broker.message.PremiumUserMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.List;


//@Configuration
public class PremiumOfferTwoStream {

    @Bean
    public KStream<String, PremiumOfferMessage> kstreamPremiumOffer(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var purchaseSerde = new JsonSerde<>(PremiumPurchaseMessage.class);
        var userSerde = new JsonSerde<>(PremiumUserMessage.class);
        var offerSerde = new JsonSerde<>(PremiumOfferMessage.class);

        var purchaseStream = builder.stream("t-commodity-premium-purchase", Consumed.with(stringSerde, purchaseSerde))
                .selectKey((k, v) -> v.getUsername());

        var filterLevel = List.of("gold", "diamond");

        var userTable = builder.table("t-commodity-premium-user", Consumed.with(stringSerde, userSerde))
                .filter((k, v) -> {
                    System.out.println("on lambda " + k + " : " + v.getLevel());
                    return filterLevel.contains(v.getLevel().toLowerCase());
                });

        userTable.toStream().print(Printed.toSysOut());

        var offerStream = purchaseStream.leftJoin(userTable, this::joiner,
                Joined.with(stringSerde, purchaseSerde, userSerde));

        offerStream.to("t-commodity-premium-offer-two", Produced.with(stringSerde, offerSerde));

        return offerStream;
    }

    private PremiumOfferMessage joiner(PremiumPurchaseMessage purchase, PremiumUserMessage user) {
        var result = new PremiumOfferMessage();

        result.setUsername(purchase.getUsername());
        result.setPurchaseNumber(purchase.getPurchaseNumber());

        if (user != null) {
            System.out.println("on joiner : " + user.getLevel());
            result.setLevel(user.getLevel());
        } else {
            result.setLevel("Free");
        }

        return result;
    }

}
