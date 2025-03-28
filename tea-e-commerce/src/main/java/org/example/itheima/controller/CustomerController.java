package org.example.itheima.controller;

import org.example.itheima.dto.PasswordUpdateDTO;
import org.example.itheima.pojo.*;
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
    @GetMapping("/users/{id}")
    public Result<CustomerInfo> queryInfoById(@PathVariable("id") Integer id){
        CustomerInfo customerInfo = customerService.queryInfoById(id);
        return Result.success(customerInfo);
    }
    @PutMapping("/users/{id}")
    public Result updateInfo(@PathVariable("id") Integer id,@RequestBody @Validated Customer customer){
        customerService.updateInfo(customer);
        return Result.success();
    }
    @PutMapping("/users/password")
    public Result updatePassword(@RequestBody PasswordUpdateDTO dto){
        customerService.updatePassword(dto);
        return Result.success();
    }
    @PostMapping("/users/address")
    public Result addAddress(@RequestBody CustomerAddress customerAddress){
        customerService.addAddress(customerAddress);
        return Result.success();
    }
    @PutMapping("/users/address")
    public Result updateAddress(@RequestBody CustomerAddress customerAddress){
        System.out.println(customerAddress);
        customerService.updateAddress(customerAddress);
        return Result.success();
    }
    @DeleteMapping("/users/address")
    public Result deleteAddress(@RequestParam Long id, @RequestParam Long addressId) {
        //System.out.println(id + " " + addressId);
        customerService.deleteAddress(id, addressId);
        return Result.success();
    }
}
