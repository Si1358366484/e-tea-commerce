package org.example.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.itheima.dto.OrderInfoDTO;
import org.example.itheima.dto.OrderQueryDTO;
import org.example.itheima.dto.OrderReturnDTO;
import org.example.itheima.mapper.OrderMapper;
import org.example.itheima.pojo.*;
import org.example.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.example.itheima.pojo.OrderStatus.*;

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
        order.setState(未付款.getDescription());
        order.setCustomerName(orders.getReceiver());
        order.setCreateTime(LocalDateTime.now());
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
        switch (order.getState()) {
            case "已付款":
                order.setState(OrderStatus.已付款.getDescription());
                break;
            case "已发货":
                order.setState(OrderStatus.已发货.getDescription());
                break;
            case "已收货":
                order.setState(OrderStatus.已收货.getDescription());
                break;
            case "申请退货":
                order.setState(OrderStatus.申请退货.getDescription());
                break;
            case "已退货":
                order.setState(OrderStatus.已退货.getDescription());
                break;
            default:
                order.setState(OrderStatus.未付款.getDescription());
                break;
        }
        orderMapper.updateOrder(order);
    }

    @Override
    public void updateAddress(String orderId, String address,String name,String phone) {
        orderMapper.updateAddress(orderId,address,name,phone);
    }

    @Override
    public OrderReturnDTO orderQueryList(OrderQueryDTO dto) {
        OrderReturnDTO orderReturnDTO = new OrderReturnDTO();
        //开启分页查询
        PageHelper.startPage(dto.getPage(),dto.getPageSize());
        List<Order> orders = orderMapper.orderQueryList(dto.getStatus(),dto.getUsername());
        Page<Order> orderPage = (Page<Order>) orders;
        List<OrderInfoDTO> rows = new java.util.ArrayList<>();
        for (Order order : orders) {
            OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
            orderInfoDTO.setProducts(new ArrayList<>());
            String orderReference = order.getOrderReference();
            List<OrderItem> orderItems = orderMapper.orderItemSearch(orderReference);
            for (OrderItem orderItem : orderItems) {
                String name = orderItem.getName();
                List<Tea> teas = orderMapper.teaSearch(name);
                for (Tea tea : teas) {
                    tea.setQuantity(orderItem.getQuantity());
                }
                orderInfoDTO.getProducts().addAll(teas);
            }
            orderInfoDTO.setOrderId(order.getOrderReference());
            orderInfoDTO.setTotalPrice(order.getOrderAmounts());
            orderInfoDTO.setReceiver(order.getCustomerName());
            orderInfoDTO.setStatus(order.getState());
            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setAddress(order.getShippingAddress());
            customerAddress.setName(order.getCustomerName());
            customerAddress.setPhone(order.getPhone());
            orderInfoDTO.setAddress(customerAddress);
            rows.add(orderInfoDTO);
        }
        orderReturnDTO.setRows(rows);
        orderReturnDTO.setTotal((int) orderPage.getTotal());
        return orderReturnDTO;
    }

    @Override
    public void updateOrder(Order order) {
        if (order.getState().equals("已发货")) {
            order.setState(已发货.getDescription());
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
