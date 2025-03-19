package com.iv.kafkademo2order.command.service;

import com.iv.kafkademo2order.api.request.FeedbackRequest;
import com.iv.kafkademo2order.command.action.FeedbackAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackAction action;

    public void createFeedback(FeedbackRequest request) {
        action.publishToKafka(request);
    }

}
