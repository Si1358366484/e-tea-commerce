package org.example.itheima.controller;

import jakarta.validation.constraints.Pattern;
import org.example.itheima.pojo.Result;
import org.example.itheima.pojo.User;
import org.example.itheima.service.UserService;
import org.example.itheima.utils.JwtUtil;
import org.example.itheima.utils.Md5Util;
import org.example.itheima.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.example.itheima.utils.JwtUtil.parseToken;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户
        User u = userService.findByUsername(username);
        if (u == null) {
            //没有占用
            //注册
            userService.register(username, password);
            return Result.success();
        } else {
            //占用
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //根据用户名查询用户
        User loginuser = userService.findByUsername(username);
        //判断用户是否存在
        if (loginuser == null) {
            return Result.error("用户名错误");
        } else {
            //判断密码是否正确
            if (Md5Util.getMD5String(password).equals(loginuser.getPassword())) {
                //生成token
                Map<String, Object> claims = new HashMap<>();
                claims.put("id",loginuser.getId());
                claims.put("username",loginuser.getUsername());
                String token = JwtUtil.genToken(claims);
                //生成的token存到redis中
                ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
                operations.set(token,token,12, TimeUnit.HOURS);
                return Result.success(token);
            } else {
                return Result.error("密码错误");
            }
        }
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        //修改用户信息
        userService.update(user);
        return Result.success();
    }
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        //修改用户头像
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String tokens){
        System.out.println(params);
        //校验参数
        String oldPwd = params.get("oldPassword");
        String newPwd = params.get("newPassword");
        String rePwd = params.get("rePassword");

        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }
        //原密码是否正确
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }

        if (!rePwd.equals(newPwd)){ 
            return Result.error("两次密码不一致");
        }

        //修改用户密码
        userService.updatePwd(newPwd);
        //删除对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String token = tokens.split(",")[0].replace("Bearer ", "");
        operations.getOperations().delete(token);
        return Result.success();
    }
}
