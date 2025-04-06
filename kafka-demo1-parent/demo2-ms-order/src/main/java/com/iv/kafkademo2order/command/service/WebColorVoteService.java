package com.iv.kafkademo2order.command.service;

import com.iv.kafkademo2order.api.request.WebColorVoteRequest;
import com.iv.kafkademo2order.command.action.WebColorVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebColorVoteService {

    @Autowired
    private WebColorVoteAction action;

    public void createColorVote(WebColorVoteRequest request) {
        action.publishToKafka(request);
    }

}

