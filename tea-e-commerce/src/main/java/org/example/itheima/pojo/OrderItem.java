package org.example.itheima.pojo;

import lombok.Data;

@Data
public class OrderItem {
    private String orderReference;
    private String name;
    private Integer quantity;
    private Float price;
}
