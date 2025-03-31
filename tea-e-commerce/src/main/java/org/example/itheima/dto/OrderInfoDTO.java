package org.example.itheima.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.itheima.pojo.CustomerAddress;
import org.example.itheima.pojo.Tea;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderInfoDTO {
    String orderId;
    Float totalPrice;
    String receiver;
    String status;
    List<Tea> products;
    CustomerAddress address;
}
