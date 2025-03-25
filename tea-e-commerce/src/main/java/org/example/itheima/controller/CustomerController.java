package org.example.itheima.controller;

import org.example.itheima.pojo.Customer;
import org.example.itheima.pojo.Order;
import org.example.itheima.pojo.Result;
import org.example.itheima.pojo.User;
import org.example.itheima.service.CustomerService;
import org.example.itheima.utils.JwtUtil;
import org.example.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/register")
    public Result register(@RequestBody Customer customer){
        String password = customer.getPassword();
        String username = customer.getUsername();
        customerService.register(password, username);
        return Result.success();
    }
    @PostMapping("/login")
    public Result login(@RequestBody Customer customer){
        String password = customer.getPassword();
        String username = customer.getUsername();
        Customer c = customerService.login(username);
        if (c == null) {
            return Result.error("用户名错误");
        }else {
            if (c.getPassword().equals(password)){
                //生成token
                Map<String,Object> claims = new HashMap<>();
                claims.put("id",c.getId());
                claims.put("username",username);
                String token = JwtUtil.genToken(claims);
                c.setToken(token);
                return Result.success(c);
            }else {
                return Result.error("密码错误");
            }
        }
    }
    @GetMapping("/orderList/{name}")
    public Result<List<Order>> orderList(@PathVariable("name") String name){
        List<Order> orderList = customerService.orderList(name);
        return Result.success(orderList);
    }
    @PutMapping("/update")
    public Result update(@RequestBody Customer customer){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        customer.setId(id);
        System.out.println(customer);
        if (customer.getPassword() == null && customer.getName() == null ){
            return Result.error("没有需要修改的数据");
        }
        //修改用户信息
        customerService.update(customer);
        return Result.success();
    }
}
