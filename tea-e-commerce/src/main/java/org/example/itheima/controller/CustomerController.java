package org.example.itheima.controller;

import org.example.itheima.pojo.Customer;
import org.example.itheima.pojo.Result;
import org.example.itheima.service.CustomerService;
import org.example.itheima.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
}
