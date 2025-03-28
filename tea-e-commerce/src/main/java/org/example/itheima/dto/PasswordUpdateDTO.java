package org.example.itheima.dto;

import lombok.Data;

@Data
public class PasswordUpdateDTO {
    private Integer id;
    private String oldPassword;
    private String newPassword;
}
