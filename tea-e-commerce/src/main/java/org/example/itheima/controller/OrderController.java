package org.example.itheima.controller;

import org.example.itheima.pojo.Order;
import org.example.itheima.pojo.Result;
import org.example.itheima.pojo.User;
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
    @PutMapping("/orderUpdate")
    public Result updateOrder(@RequestBody Order order){
        orderService.updateOrder(order);
        return Result.success("修改成功");
    }
    @GetMapping("/orderSearch/{orderReference}")
    public Result<Order> orderSearch(@PathVariable("orderReference") String orderReference){
        Order order = orderService.orderSearch(orderReference);
        return Result.success(order);
    }
}

