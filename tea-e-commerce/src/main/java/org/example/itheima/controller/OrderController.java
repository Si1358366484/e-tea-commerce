package org.example.itheima.controller;

import org.example.itheima.pojo.*;
import org.example.itheima.service.OrderService;
import org.example.itheima.utils.Md5Util;
import org.example.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/orders")
    public Result addOrder(@RequestBody OrderData orders){
        String orderId = orderService.addOrder(orders);
        return Result.success(orderId);
    }
    @GetMapping("/orders/{orderId}")
    public Result<OrderDetail> getOrderById(@PathVariable("orderId") String orderId){
        OrderDetail od = orderService.getOrderById(orderId);
        return Result.success(od);
    }
    //下面是管理员端
    @GetMapping("/admin/orderList")
    public Result<List<Order>> orderList(){
        List<Order> orderList = orderService.orderList();
        return Result.success(orderList);
    }
    @PutMapping("/admin/orderUpdate")
    public Result updateOrder(@RequestBody Order order){
        orderService.updateOrder(order);
        return Result.success("修改成功");
    }
    @GetMapping("/admin/orderSearch/{orderReference}")
    public Result<Order> orderSearch(@PathVariable("orderReference") String orderReference){
        Order order = orderService.orderSearch(orderReference);
        return Result.success(order);
    }
}

