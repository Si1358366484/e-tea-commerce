package org.example.itheima.pojo;

import lombok.Data;

@Data
public class Order {
    String orderReference;
    Float orderAmounts;
    String customerName;
    String state;
    String shippingAddress;
}
