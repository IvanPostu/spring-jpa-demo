package com.iv.demo2.kafka.stream.stream.premium;


import com.iv.kafkademo2common.broker.message.PremiumOfferMessage;
import com.iv.kafkademo2common.broker.message.PremiumPurchaseMessage;
import com.iv.kafkademo2common.broker.message.PremiumUserMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.List;


//@Configuration
public class PremiumOfferThreeStream {

    @Bean
    public KStream<String, PremiumOfferMessage> kstreamPremiumOffer(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var purchaseSerde = new JsonSerde<>(PremiumPurchaseMessage.class);
        var userSerde = new JsonSerde<>(PremiumUserMessage.class);
        var offerSerde = new JsonSerde<>(PremiumOfferMessage.class);

        var purchaseStream = builder.stream("t-commodity-premium-purchase", Consumed.with(stringSerde, purchaseSerde))
                .selectKey((k, v) -> v.getUsername());

        var filterLevel = List.of("gold", "diamond");

        builder.stream("t-commodity-premium-user", Consumed.with(stringSerde, userSerde))
                .filter((k, v) -> filterLevel.contains(v.getLevel().toLowerCase()))
                .to("t-commodity-premium-user-filtered", Produced.with(stringSerde, userSerde));

        var userTable = builder.globalTable("t-commodity-premium-user-filtered", Consumed.with(stringSerde, userSerde));

        var offerStream = purchaseStream.join(userTable, (key, value) -> key, this::joiner);

//		var offerStream = purchaseStream.join(userTable, (key, value) -> value.getUsername(), this::joiner);

        offerStream.to("t-commodity-premium-offer-three", Produced.with(stringSerde, offerSerde));

        return offerStream;
    }

    private PremiumOfferMessage joiner(PremiumPurchaseMessage purchase, PremiumUserMessage user) {
        var result = new PremiumOfferMessage();

        result.setUsername(purchase.getUsername());
        result.setPurchaseNumber(purchase.getPurchaseNumber());
        result.setLevel(user.getLevel());

        return result;
    }

}

