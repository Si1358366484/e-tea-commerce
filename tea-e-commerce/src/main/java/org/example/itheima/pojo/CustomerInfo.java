package org.example.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.itheima.dto.AddressDTO;
import org.example.itheima.dto.CustomerDTO;
import org.example.itheima.dto.OrderDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerInfo {
    private CustomerDTO userInfo;
    private List<OrderDTO> orders;
    private List<AddressDTO> addresses;
}

