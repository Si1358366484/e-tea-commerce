package org.example.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    String orderReference;
    Float orderAmounts;
    String customerName;
    String state;
    String shippingAddress;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;
    String phone;
}
