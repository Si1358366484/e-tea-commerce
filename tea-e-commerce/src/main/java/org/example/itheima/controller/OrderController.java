package org.example.itheima.controller;

import org.example.itheima.dto.AddressDTO;
import org.example.itheima.dto.MoneyBackDTO;
import org.example.itheima.dto.OrderQueryDTO;
import org.example.itheima.dto.OrderReturnDTO;
import org.example.itheima.pojo.*;
import org.example.itheima.service.CustomerService;
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
    @Autowired
    private CustomerService customerService;
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
    @PutMapping("/orders/address/{orderId}")
    public Result updateAddress(@PathVariable("orderId") String orderId,@RequestBody Map<String, Object> requestBody){
        Map<String, Object> addressMap = (Map<String, Object>) requestBody.get("address");
        System.out.println(addressMap);
        String address = (String) addressMap.get("address");
        String name = (String) addressMap.get("name");
        String phone = (String) addressMap.get("phone");
        orderService.updateAddress(orderId,address,name,phone);
        return Result.success("修改成功");
    }
    @GetMapping("/orders")
    public Result<OrderReturnDTO> orderList(OrderQueryDTO dto){
        OrderReturnDTO orderReturnDTO = orderService.orderQueryList(dto);
        //System.out.println(orderReturnDTO);
        return Result.success(orderReturnDTO);
    }
    @PutMapping("/orders/status/{orderId}")
    public Result updateOrderState(@PathVariable("orderId") String orderId,@RequestBody Map<String, String> requestBody){
        String state = requestBody.get("status");
        Order order = orderService.orderSearch(orderId);
        order.setState(state);
        orderService.updateOrderState(order);
        return Result.success("修改成功");
    }
    @PutMapping("/orders/balancePay/{orderId}")
    public Result balancePay(@PathVariable("orderId") String orderId,@RequestBody Map<String, String> requestBody){
        String state = requestBody.get("status");
        Double balance = Double.parseDouble(requestBody.get("balance"));
        Order order = orderService.orderSearch(orderId);
        order.setState(state);
        Double price = balance - Double.valueOf(order.getOrderAmounts());
        orderService.updateOrderState(order);
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        orderService.updateBalance(userId,price);
        return Result.success("修改成功");
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
    @PutMapping("/admin/moneyBack")
    public Result moneyBack(@RequestBody MoneyBackDTO dto){
        Double balance = dto.getBalance();
        String orderReference = dto.getOrderReference();
        Order order = orderService.orderSearch(orderReference);
        int i = order.getCustomerId();
        customerService.updateBalance(i,balance);
        return Result.success("修改成功");
    }
    @GetMapping("/admin/orderSearch/{orderReference}")
    public Result<Order> orderSearch(@PathVariable("orderReference") String orderReference){
        Order order = orderService.orderSearch(orderReference);
        return Result.success(order);
    }

}

