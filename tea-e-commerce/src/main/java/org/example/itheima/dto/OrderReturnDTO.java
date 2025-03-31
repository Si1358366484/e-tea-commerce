package org.example.itheima.dto;

import lombok.Data;

import java.util.List;
@Data
public class OrderReturnDTO {
    private int total;
    private List<OrderInfoDTO> rows;
}
