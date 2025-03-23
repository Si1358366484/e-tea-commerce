package org.example.itheima.pojo;

import lombok.Data;

@Data
public class Order {
    Integer id;
    String orderReference;
    Float orderAmounts;
    String customerName;
    String state;
    String shippingAddress;
}
