package com.iv.kafkademo2order.command.service;

import com.iv.kafkademo2order.api.request.SubscriptionUserRequest;
import com.iv.kafkademo2order.command.action.SubscriptionUserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SubscriptionUserService {

    @Autowired
    private SubscriptionUserAction action;

    public void createUser(SubscriptionUserRequest request) {
        action.publishToKafka(request);
    }

}
