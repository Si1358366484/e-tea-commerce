package org.example.itheima.service.Impl;

import org.example.itheima.mapper.AdminMapper;
import org.example.itheima.pojo.Admin;
import org.example.itheima.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String username) {
        return adminMapper.login(username);
    }
}
