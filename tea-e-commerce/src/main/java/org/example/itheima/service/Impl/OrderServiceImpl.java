package org.example.itheima.service.Impl;

import org.example.itheima.mapper.OrderMapper;
import org.example.itheima.pojo.Order;
import org.example.itheima.pojo.OrderStatus;
import org.example.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> orderList() {
//        List<Order> orders = orderMapper.orderList();
//        for (Order order : orders){
//            order.setOrderStatus(OrderStatus.valueOf(order.getOrderStatus()).getDescription());
//        }
//        return orders;
        return orderMapper.orderList();
    }

    @Override
    public void addOrder(Order order) {
        order.setState(OrderStatus.已付款.getDescription());
        orderMapper.addOrder(order);
    }
}
