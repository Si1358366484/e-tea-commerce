package org.example.itheima.pojo;

import lombok.Data;

import java.util.List;

@Data
public class OrderData {
    private List<Tea> products;
    private float totalPrice;
    private String receiver;
}
