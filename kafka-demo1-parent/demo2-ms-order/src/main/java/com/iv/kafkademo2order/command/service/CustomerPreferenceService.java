package com.iv.kafkademo2order.command.service;

import com.iv.kafkademo2order.api.request.CustomerPreferenceShoppingCartRequest;
import com.iv.kafkademo2order.api.request.CustomerPreferenceWishlistRequest;
import com.iv.kafkademo2order.command.action.CustomerPreferenceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerPreferenceService {

    @Autowired
    private CustomerPreferenceAction action;

    public void createShoppingCart(CustomerPreferenceShoppingCartRequest request) {
        action.publishShoppingCart(request);
    }

    public void createWishlist(CustomerPreferenceWishlistRequest request) {
        action.publishWishlist(request);
    }
}
