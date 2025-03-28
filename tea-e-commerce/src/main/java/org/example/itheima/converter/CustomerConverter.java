package org.example.itheima.converter;


import org.example.itheima.dto.CustomerDTO;
import org.example.itheima.pojo.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    public CustomerDTO convertToDTO(Customer customer) {
        if (customer == null) return null;
        //流式构建模式
        return CustomerDTO.builder()
                .name(customer.getName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .avatar(customer.getAvatar())
                .build();
    }
}