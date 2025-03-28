package org.example.itheima.service.Impl;

import org.example.itheima.converter.AddressConverter;
import org.example.itheima.converter.CustomerConverter;
import org.example.itheima.converter.OrderConverter;
import org.example.itheima.dto.CustomerDTO;
import org.example.itheima.dto.OrderDTO;
import org.example.itheima.dto.PasswordUpdateDTO;
import org.example.itheima.mapper.CustomerMapper;
import org.example.itheima.pojo.Customer;
import org.example.itheima.pojo.CustomerAddress;
import org.example.itheima.pojo.CustomerInfo;
import org.example.itheima.pojo.Order;
import org.example.itheima.service.CustomerService;
import org.example.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerConverter customerConverter;
    @Autowired
    private OrderConverter orderConverter;
    @Autowired
    private AddressConverter addressConverter;
    @Override
    public void register(String password, String username) {
        customerMapper.register(password,username);
    }

    @Override
    public Customer login(String username) {
        return customerMapper.login(username);
    }

    @Override
    public CustomerInfo queryInfoById(Integer id) {
        CustomerInfo customerInfo = new CustomerInfo();
        Customer customer = customerMapper.queryInfoById(id);
        List<Order> orders = customerMapper.newOrder(customer.getId());
        //List<Order> orders = customerMapper.orderList(customer.getId());
        List<CustomerAddress> addresses = customerMapper.addressList(customer.getId());
        //使用DTO转换器赋值
        customerInfo.setUserInfo(customerConverter.convertToDTO(customer));
        customerInfo.setOrders(orderConverter.convertToDTOList(orders));
        customerInfo.setAddresses(addressConverter.convertToDTOList(addresses));
        return customerInfo;
    }

    @Override
    public void updateInfo(Customer customer) {
        Map<String,Object> map = ThreadLocalUtil.get();
        customer.setId((Integer) map.get("id"));
        customerMapper.updateInfo(customer);
    }

    @Override
    public void updatePassword(PasswordUpdateDTO dto) {
        customerMapper.updatePassword(dto);
    }

    @Override
    public void addAddress(CustomerAddress customerAddress) {
        customerMapper.addAddress(customerAddress);
    }

    @Override
    public void updateAddress(CustomerAddress customerAddress) {
        customerMapper.updateAddress(customerAddress);
    }

    @Override
    public void deleteAddress(Long id, Long addressId) {
        customerMapper.deleteAddress(id,addressId);
    }
}
