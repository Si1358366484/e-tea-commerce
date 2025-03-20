package org.example.itheima.service;

import org.example.itheima.pojo.Admin;
import org.hibernate.validator.constraints.URL;

public interface AdminService {
    Admin login(String username);

    Admin findByName(String name);

    void updatePwd(String newPwd);

    void updateAvatar(@URL String avatarUrl);
}
