package org.example.itheima.controller;

import org.example.itheima.pojo.Admin;
import org.example.itheima.pojo.Result;
import org.example.itheima.service.AdminService;
import org.example.itheima.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RequestMapping("/admin")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/login")
    public Result login(String username, String password) {
        Admin admin = adminService.login(username);
        if (admin == null){
            return Result.error("用户名错误");
        }else {
            if (admin.getPassword().equals(password)){
                Map<String,Object> claims = new HashMap<>();
                claims.put("id",admin.getId());
                claims.put("username",username);
                String token = JwtUtil.genToken(claims);
                admin.setToken(token);
                return Result.success(admin);
            }else {
                return Result.error("密码错误");
            }
        }
    }
}
