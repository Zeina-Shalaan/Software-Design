package com.crm.order.controller;

import com.crm.order.model.Order;
import com.crm.order.repository.OrderRepository;

public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public Order retrieveOrder(String orderId) {
        return orderRepository.findById(orderId);
    }

    public void updateOrder(Order order) {
        orderRepository.update(order);
    }

    public void cancelOrder(String orderId) {
        Order o = orderRepository.findById(orderId);
        if (o != null) {
            o.cancel();
            orderRepository.update(o);
        }
    }
}
