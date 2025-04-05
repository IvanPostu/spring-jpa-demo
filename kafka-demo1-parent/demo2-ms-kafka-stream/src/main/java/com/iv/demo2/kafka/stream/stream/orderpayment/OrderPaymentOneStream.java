package com.iv.demo2.kafka.stream.stream.orderpayment;

import com.iv.demo2.kafka.stream.stream.orderpayment.util.OnlineOrderTimestampExtractor;
import com.iv.demo2.kafka.stream.stream.orderpayment.util.OnlinePaymentTimestampExtractor;
import com.iv.kafkademo2common.broker.message.OnlineOrderMessage;
import com.iv.kafkademo2common.broker.message.OnlineOrderPaymentMessage;
import com.iv.kafkademo2common.broker.message.OnlinePaymentMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

//@Configuration
public class OrderPaymentOneStream {

    @Bean
    public KStream<String, OnlineOrderMessage> kstreamOrderPayment(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OnlineOrderMessage.class);
        var paymentSerde = new JsonSerde<>(OnlinePaymentMessage.class);
        var orderPaymentSerde = new JsonSerde<>(OnlineOrderPaymentMessage.class);

        var orderStream = builder.stream("t-commodity-online-order",
                Consumed.with(stringSerde, orderSerde, new OnlineOrderTimestampExtractor(), null));

        var paymentStream = builder.stream("t-commodity-online-payment",
                Consumed.with(stringSerde, paymentSerde, new OnlinePaymentTimestampExtractor(), null));

        orderStream
                .join(paymentStream, this::joinOrderPayment,
                        JoinWindows.of(Duration.ofHours(1l)).grace(Duration.ofMillis(0l)),
                        StreamJoined.with(stringSerde, orderSerde, paymentSerde))
                .to("t-commodity-join-order-payment-one", Produced.with(stringSerde, orderPaymentSerde));

        return orderStream;
    }

    private OnlineOrderPaymentMessage joinOrderPayment(OnlineOrderMessage order, OnlinePaymentMessage payment) {
        var result = new OnlineOrderPaymentMessage();

        result.setOnlineOrderNumber(order.getOnlineOrderNumber());
        result.setOrderDateTime(order.getOrderDateTime());
        result.setTotalAmount(order.getTotalAmount());
        result.setUsername(order.getUsername());

        result.setPaymentDateTime(payment.getPaymentDateTime());
        result.setPaymentMethod(payment.getPaymentMethod());
        result.setPaymentNumber(payment.getPaymentNumber());

        return result;
    }

}
