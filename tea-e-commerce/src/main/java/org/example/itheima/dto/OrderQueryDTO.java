package org.example.itheima.dto;

import lombok.Data;

@Data
public class OrderQueryDTO {
    private String username;
    private String status;
    private Integer page;
    private Integer pageSize;
}
