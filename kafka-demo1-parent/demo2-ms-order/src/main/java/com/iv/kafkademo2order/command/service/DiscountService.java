package com.iv.kafkademo2order.command.service;

import com.iv.kafkademo2order.api.request.DiscountRequest;
import com.iv.kafkademo2order.command.action.DiscountAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    @Autowired
    private DiscountAction action;

    public void createDiscount(DiscountRequest request) {
        action.publishToKafka(request);
    }

}
