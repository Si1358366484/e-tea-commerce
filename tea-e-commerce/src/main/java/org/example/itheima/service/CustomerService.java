package org.example.itheima.service;

import org.example.itheima.pojo.Customer;
import org.example.itheima.pojo.Order;

import java.util.List;

public interface CustomerService {
    void register(String password, String username);

    Customer login(String username);

    List<Order> orderList(String name);

    void update(Customer customer);
}
