package com.iv.kafkademo2order.command.service;

import com.iv.kafkademo2order.api.request.OrderRequest;
import com.iv.kafkademo2order.command.action.OrderAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderAction orderAction;

    public String saveOrder(OrderRequest request) {
        var order = orderAction.convertToOrder(request);
        orderAction.saveToDatabase(order);

        // flatten message & publish
        order.getItems().forEach(orderAction::publishToKafka);

        return order.getOrderNumber();
    }

}
