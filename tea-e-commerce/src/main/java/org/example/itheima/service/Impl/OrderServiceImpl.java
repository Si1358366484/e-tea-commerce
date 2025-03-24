package org.example.itheima.service.Impl;

import org.example.itheima.mapper.OrderMapper;
import org.example.itheima.pojo.*;
import org.example.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> orderList() {
        return orderMapper.orderList();
    }

    @Override
    public String addOrder(OrderData orders) {
        //创建订单
        Order order = new Order();
        String uuidStr = UUID.randomUUID().toString().replaceAll("[^0-9]", ""); // 去除非数字
        String numericUUID = uuidStr.substring(0, 8); // 取前8位
        order.setOrderReference(numericUUID);
        order.setOrderAmounts(orders.getTotalPrice());
        order.setState(OrderStatus.未付款.getDescription());
        order.setCustomerName(orders.getReceiver());
        order.setShippingAddress("广州软件学院");
        orderMapper.addOrder(order);
        //创建订单明细表
        for (Tea tea : orders.getProducts()) {
            OrderItem oi = new OrderItem();
            oi.setOrderReference(numericUUID);
            oi.setName(tea.getName());
            oi.setQuantity(tea.getQuantity());
            oi.setPrice(tea.getPrice());
            orderMapper.addOrderItem(oi);
        }
        return numericUUID;
    }

    @Override
    public OrderDetail getOrderById(String orderId) {
        OrderDetail od = new OrderDetail();
        Order order = orderMapper.orderSearch(orderId);
        List<OrderItem> oi = orderMapper.orderItemSearch(orderId);
        od.setOrderId(orderId);
        od.setTotalPrice(order.getOrderAmounts());
        od.setReceiver(order.getCustomerName());
        od.setPaymentStatus(order.getState());
        od.setProducts(oi);
        return od;
    }

    @Override
    public void updateOrderState(Order order) {
        System.out.println(order);
        order.setState(OrderStatus.已付款.getDescription());
        orderMapper.updateOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        if (order.getState().equals("已发货")) {
            order.setState(OrderStatus.已发货.getDescription());
        } else {
            order.setState(OrderStatus.已退货.getDescription());
        }
        orderMapper.updateOrder(order);
    }

    @Override
    public Order orderSearch(String orderReference) {
        return orderMapper.orderSearch(orderReference);
    }
}
