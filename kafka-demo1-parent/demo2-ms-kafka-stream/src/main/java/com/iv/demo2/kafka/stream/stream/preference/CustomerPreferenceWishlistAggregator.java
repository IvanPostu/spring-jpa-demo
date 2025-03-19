package com.iv.demo2.kafka.stream.stream.preference;

import com.iv.kafkademo2common.broker.message.CustomerPreferenceAggregateMessage;
import com.iv.kafkademo2common.broker.message.CustomerPreferenceWishlistMessage;
import org.apache.kafka.streams.kstream.Aggregator;

public class CustomerPreferenceWishlistAggregator
        implements Aggregator<String, CustomerPreferenceWishlistMessage, CustomerPreferenceAggregateMessage> {

    @Override
    public CustomerPreferenceAggregateMessage apply(String key, CustomerPreferenceWishlistMessage value,
                                                    CustomerPreferenceAggregateMessage aggregate) {
        aggregate.putWishlistItem(value.getItemName(), value.getWishlistDatetime());

        return aggregate;
    }

}
