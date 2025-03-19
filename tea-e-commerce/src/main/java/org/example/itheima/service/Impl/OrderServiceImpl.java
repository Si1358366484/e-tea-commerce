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
        return orderMapper.orderList();
    }

    @Override
    public void addOrder(Order order) {
        order.setState(OrderStatus.已付款.getDescription());
        orderMapper.addOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        if (order.getState().equals("已发货")){
            order.setState(OrderStatus.已发货.getDescription());
        }else {
            order.setState(OrderStatus.已退货.getDescription());
        }
        orderMapper.updateOrder(order);
    }

    @Override
    public Order orderSearch(String orderReference) {
        return orderMapper.orderSearch(orderReference);
    }
}
