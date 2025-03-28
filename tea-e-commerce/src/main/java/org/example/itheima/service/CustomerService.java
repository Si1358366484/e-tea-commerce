package org.example.itheima.service;

import org.example.itheima.dto.PasswordUpdateDTO;
import org.example.itheima.pojo.Customer;
import org.example.itheima.pojo.CustomerAddress;
import org.example.itheima.pojo.CustomerInfo;
import org.example.itheima.pojo.Order;

import java.util.List;

public interface CustomerService {
    void register(String password, String username);

    Customer login(String username);

    CustomerInfo queryInfoById(Integer id);

    void updateInfo(Customer customer);

    void updatePassword(PasswordUpdateDTO dto);

    void addAddress(CustomerAddress customerAddress);

    void updateAddress(CustomerAddress customerAddress);

    void deleteAddress(Long id, Long addressId);
}
