package org.example.itheima.converter;


import org.example.itheima.dto.OrderDTO;
import org.example.itheima.pojo.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public OrderDTO convertToDTO(Order source) {
        return OrderDTO.builder()
                .orderId(source.getOrderReference())
                .totalPrice(source.getOrderAmounts())
                .createTime(formatDateTime(source.getCreateTime()))
                .paymentStatus(convertStatus(source.getState()))
                .build();
    }

    public List<OrderDTO> convertToDTOList(List<Order> orders) {
        return orders.stream()//将list转化为流
                .map(this::convertToDTO)//将每个order转化为orderDTO
                .collect(Collectors.toList());//将流转化为list
    }

    private String formatDateTime(LocalDateTime time) {
        return time != null ?
                time.format(DATE_FORMATTER) :
                "无时间记录";
    }

    private String convertStatus(String status) {
        return switch (status) {
            case "已付款" -> "已付款";
            case "已发货" -> "已发货";
            case "已退货" -> "已退货";
            case "未付款" -> "未付款";
            default -> "未知状态";
        };
    }
}