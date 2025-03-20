package com.iv.kafkademo2order.command.service;

import com.iv.kafkademo2order.api.request.FlashSaleVoteRequest;
import com.iv.kafkademo2order.command.action.FlashSaleVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlashSaleVoteService {

    @Autowired
    private FlashSaleVoteAction action;

    public void createFlashSaleVote(FlashSaleVoteRequest request) {
        action.publishToKafka(request);
    }

}
