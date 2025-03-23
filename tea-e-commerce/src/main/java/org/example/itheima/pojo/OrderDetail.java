package org.example.itheima.pojo;

import lombok.Data;

import java.util.List;
@Data
public class OrderDetail {
    private String orderId;
    private Float totalPrice;
    private String receiver;
    private List<OrderItem> products;
    private String paymentStatus;
}
