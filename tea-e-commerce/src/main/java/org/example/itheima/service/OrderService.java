package org.example.itheima.service;

import org.example.itheima.pojo.Order;
import org.example.itheima.pojo.OrderData;
import org.example.itheima.pojo.OrderDetail;
import org.example.itheima.pojo.Tea;

import java.util.List;

public interface OrderService {
    List<Order> orderList();

    void updateOrder(Order order);

    Order orderSearch(String orderReference);

    String addOrder(OrderData orders);

    OrderDetail getOrderById(String orderId);
}
