package org.example.itheima.service;

import org.example.itheima.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> orderList();

    void addOrder(Order order);

    void updateOrder(Order order);

    Order orderSearch(String orderReference);
}
