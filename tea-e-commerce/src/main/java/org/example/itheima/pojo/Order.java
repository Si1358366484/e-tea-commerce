package org.example.itheima.pojo;

import lombok.Data;

@Data
public class Order {
    Integer id;
    String orderReference;
    Integer teaNumber;
    String teaName;
    Float orderAmounts;
    String customerName;
    String state;
}
