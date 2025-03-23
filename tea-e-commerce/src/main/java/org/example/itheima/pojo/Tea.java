package org.example.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Tea {
    private Integer id;
    private String category;
    private String name;
    private String description;
    private String origin;
    private float price;
    private String image;
    private Integer quantity;
}
