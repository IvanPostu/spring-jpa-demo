package com.iv.kafkademo2order.command.action;

import com.iv.kafkademo2common.broker.message.CustomerPreferenceShoppingCartMessage;
import com.iv.kafkademo2common.broker.message.CustomerPreferenceWishlistMessage;
import com.iv.kafkademo2order.api.request.CustomerPreferenceShoppingCartRequest;
import com.iv.kafkademo2order.api.request.CustomerPreferenceWishlistRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class CustomerPreferenceAction {

    @Autowired
    private com.iv.kafkademo2order.broker.producer.CustomerPreferenceProducer producer;

    public void publishShoppingCart(CustomerPreferenceShoppingCartRequest request) {
        var message = new CustomerPreferenceShoppingCartMessage(request.getCustomerId(), request.getItemName(),
                request.getCartAmount(), LocalDateTime.now());

        producer.publishShoppingCart(message);
    }

    public void publishWishlist(CustomerPreferenceWishlistRequest request) {
        var message = new CustomerPreferenceWishlistMessage(request.getCustomerId(), request.getItemName(),
                LocalDateTime.now());

        producer.publishWishlist(message);
    }

}
