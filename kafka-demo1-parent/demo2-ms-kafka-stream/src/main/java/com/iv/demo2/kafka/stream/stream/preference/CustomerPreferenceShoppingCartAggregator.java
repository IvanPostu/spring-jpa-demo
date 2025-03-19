package com.iv.demo2.kafka.stream.stream.preference;

import com.iv.kafkademo2common.broker.message.CustomerPreferenceAggregateMessage;
import com.iv.kafkademo2common.broker.message.CustomerPreferenceShoppingCartMessage;
import org.apache.kafka.streams.kstream.Aggregator;

public class CustomerPreferenceShoppingCartAggregator
        implements Aggregator<String, CustomerPreferenceShoppingCartMessage, CustomerPreferenceAggregateMessage> {

    @Override
    public CustomerPreferenceAggregateMessage apply(String key, CustomerPreferenceShoppingCartMessage value,
                                                    CustomerPreferenceAggregateMessage aggregate) {
        aggregate.putShoppingCartItem(value.getItemName(), value.getCartDatetime());

        return aggregate;
    }

}
