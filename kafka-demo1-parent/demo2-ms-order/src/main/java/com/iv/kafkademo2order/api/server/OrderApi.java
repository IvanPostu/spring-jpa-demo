package com.iv.kafkademo2order.api.server;

import com.iv.kafkademo2order.api.request.OrderRequest;
import com.iv.kafkademo2order.api.response.OrderResponse;
import com.iv.kafkademo2order.command.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderApi {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        // 1. save order using service
        var orderNumber = orderService.saveOrder(request);

        // 2. create response
        var orderResponse = new OrderResponse(orderNumber);

        // 3. return response
        return ResponseEntity.ok().body(orderResponse);
    }

}
