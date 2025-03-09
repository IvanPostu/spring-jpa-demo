package com.iv.kafkademo2order.command.action;

import com.iv.kafkademo2order.api.request.OrderItemRequest;
import com.iv.kafkademo2order.api.request.OrderRequest;
import com.iv.kafkademo2order.broker.message.OrderMessage;
import com.iv.kafkademo2order.entity.Order;
import com.iv.kafkademo2order.entity.OrderItem;
import com.iv.kafkademo2order.producer.OrderProducer;
import com.iv.kafkademo2order.repository.OrderItemRepository;
import com.iv.kafkademo2order.repository.OrderRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class OrderAction {

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order convertToOrder(OrderRequest request) {
        var result = new Order();

        result.setCreditCardNumber(request.getCreditCardNumber());
        result.setOrderLocation(request.getOrderLocation());
        result.setOrderDateTime(LocalDateTime.now());
        result.setOrderNumber(RandomStringUtils.randomAlphanumeric(8).toUpperCase());

        var items = request.getItems().stream().map(this::convertToOrderItem).collect(Collectors.toList());
        items.forEach(item -> item.setOrder(result));

        result.setItems(items);

        return result;
    }

    private OrderItem convertToOrderItem(OrderItemRequest itemRequest) {
        var result = new OrderItem();

        result.setItemName(itemRequest.getItemName());
        result.setPrice(itemRequest.getPrice());
        result.setQuantity(itemRequest.getQuantity());

        return result;
    }

    public void saveToDatabase(Order order) {
        orderRepository.save(order);
        order.getItems().forEach(orderItemRepository::save);
    }

    public void publishToKafka(OrderItem orderItem) {
        var orderMessage = new OrderMessage();

        orderMessage.setItemName(orderItem.getItemName());
        orderMessage.setPrice(orderItem.getPrice());
        orderMessage.setQuantity(orderItem.getQuantity());

        var order = orderItem.getOrder();
        orderMessage.setOrderDateTime(order.getOrderDateTime());
        orderMessage.setOrderLocation(order.getOrderLocation());
        orderMessage.setOrderNumber(order.getOrderNumber());
        orderMessage.setCreditCardNumber(order.getCreditCardNumber());

        orderProducer.publish(orderMessage);
    }

}
