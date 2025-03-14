package org.example.itheima.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Tea {
    private Integer id;
    private String category;
    private String name;
    private String description;
    private LocalDateTime expiration_date;
    private String origin_place;
    private LocalDateTime production_time;
    private float price;
    private String image;
}
