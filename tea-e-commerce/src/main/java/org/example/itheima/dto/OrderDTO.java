package org.example.itheima.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO{
    private String orderId;
    private String createTime;
    private String paymentStatus;
    private Float totalPrice;
}
