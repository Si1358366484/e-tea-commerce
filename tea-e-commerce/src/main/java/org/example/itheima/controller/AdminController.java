package org.example.itheima.controller;

import org.example.itheima.pojo.Admin;
import org.example.itheima.pojo.Result;
import org.example.itheima.pojo.User;
import org.example.itheima.service.AdminService;
import org.example.itheima.utils.JwtUtil;
import org.example.itheima.utils.Md5Util;
import org.example.itheima.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        //校验参数
        String oldPwd = params.get("oldPassword");
        String newPwd = params.get("newPassword");
        String rePwd = params.get("rePassword");

        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }
        //原密码是否正确
        Map<String, Object> map = ThreadLocalUtil.get();
        System.out.println(map);
        String name = (String) map.get("username");
        Admin loginAdmin = adminService.findByName(name);
        if (!loginAdmin.getPassword().equals(oldPwd)){
            return Result.error("原密码错误");
        }
        //修改用户密码
        adminService.updatePwd(newPwd);
        return Result.success();
    }
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        adminService.updateAvatar(avatarUrl);
        return Result.success();
    }
}
