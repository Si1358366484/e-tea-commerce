package org.example.itheima.service;

import org.example.itheima.dto.OrderQueryDTO;
import org.example.itheima.dto.OrderReturnDTO;
import org.example.itheima.pojo.*;

import java.util.List;

public interface OrderService {
    List<Order> orderList();

    void updateOrder(Order order);

    Order orderSearch(String orderReference);

    String addOrder(OrderData orders);

    OrderDetail getOrderById(String orderId);

    void updateOrderState(Order order);

    void updateAddress(String orderId, String addresses,String name,String phone);

    OrderReturnDTO orderQueryList(OrderQueryDTO dto);
}
