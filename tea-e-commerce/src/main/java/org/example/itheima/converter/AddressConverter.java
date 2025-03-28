package org.example.itheima.converter;

import org.example.itheima.dto.AddressDTO;
import org.example.itheima.pojo.CustomerAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressConverter {
    public AddressDTO convertToDTO(CustomerAddress source) {
        if (source == null) return null;
        return AddressDTO.builder()
                .addressId(source.getAddressId())
                .phone(source.getPhone())
                .name(source.getName())
                .address(source.getAddress())
                .build();
    }
    public List<AddressDTO> convertToDTOList(List<CustomerAddress> addresses) {
        return addresses.stream()//将list转化为流
                .map(this::convertToDTO)//将每个order转化为orderDTO
                .collect(Collectors.toList());//将流转化为list
    }
}
