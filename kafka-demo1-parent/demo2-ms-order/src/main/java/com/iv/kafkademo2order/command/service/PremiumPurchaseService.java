package com.iv.kafkademo2order.command.service;

import com.iv.kafkademo2order.api.request.PremiumPurchaseRequest;
import com.iv.kafkademo2order.command.action.PremiumPurchaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PremiumPurchaseService {

    @Autowired
    private PremiumPurchaseAction action;

    public void createPurchase(PremiumPurchaseRequest request) {
        action.publishToKafka(request);
    }

}
