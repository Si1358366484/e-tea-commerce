package org.example.itheima.service;

import org.example.itheima.pojo.Customer;

public interface CustomerService {
    void register(String password, String username);

    Customer login(String username);
}
