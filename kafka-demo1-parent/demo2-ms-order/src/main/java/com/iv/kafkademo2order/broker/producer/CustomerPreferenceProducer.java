package com.iv.kafkademo2order.broker.producer;

import com.iv.kafkademo2common.broker.message.CustomerPreferenceShoppingCartMessage;
import com.iv.kafkademo2common.broker.message.CustomerPreferenceWishlistMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerPreferenceProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishShoppingCart(CustomerPreferenceShoppingCartMessage message) {
        kafkaTemplate.send("t-commodity-customer-preference-shopping-cart", message.getCustomerId(), message);
    }

    public void publishWishlist(CustomerPreferenceWishlistMessage message) {
        kafkaTemplate.send("t-commodity-customer-preference-wishlist", message.getCustomerId(), message);
    }

}

