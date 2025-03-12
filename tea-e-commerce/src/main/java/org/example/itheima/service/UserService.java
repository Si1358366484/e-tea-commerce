package org.example.itheima.service;

import org.example.itheima.pojo.User;

import java.util.Map;

public interface UserService {
    //根据用户名查寻用户
    User findByUsername(String username);
    //注册用户
    void register(String username, String password);
    //更新用户
    void update(User user);
    //更新头像
    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
