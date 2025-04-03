package org.example.itheima.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private String username;
    private String avatar;
    private String content;
    private Integer rating;
    @JsonFormat(pattern = "MM-dd")
    private LocalDateTime time;
}
