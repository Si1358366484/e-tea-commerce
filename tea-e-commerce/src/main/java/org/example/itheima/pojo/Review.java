package org.example.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Review {
    private Integer id;
    private String customerName;
    private String teaName;
    private int grade;
    private String content;
    private String state;
    @JsonFormat(pattern = "MM-dd")
    private LocalDateTime reviewTime;
    private Integer teaId;
    private Integer customerId;
}
