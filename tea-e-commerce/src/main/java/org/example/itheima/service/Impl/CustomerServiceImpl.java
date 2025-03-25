package org.example.itheima.service.Impl;

import org.example.itheima.mapper.CustomerMapper;
import org.example.itheima.pojo.Customer;
import org.example.itheima.pojo.Order;
import org.example.itheima.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public void register(String password, String username) {
        customerMapper.register(password,username);
    }

    @Override
    public Customer login(String username) {
        return customerMapper.login(username);
    }

    @Override
    public List<Order> orderList(String name) {
        return customerMapper.orderList(name);
    }

    @Override
    public void update(Customer customer) {
        customerMapper.update(customer);
    }
}
