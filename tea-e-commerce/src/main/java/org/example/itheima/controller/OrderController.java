package org.example.itheima.controller;

import org.example.itheima.pojo.Order;
import org.example.itheima.pojo.Result;
import org.example.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/orderList")
    public Result<List<Order>> orderList(){
        List<Order> orderList = orderService.orderList();
        return Result.success(orderList);
    }
    @PostMapping("/order")
    public Result addOrder(@RequestBody Order order){
        orderService.addOrder(order);
        return Result.success("下单成功");
    }
}

