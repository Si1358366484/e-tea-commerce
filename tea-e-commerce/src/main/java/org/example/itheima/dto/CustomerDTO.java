package org.example.itheima.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO{
    private String name;
    private String phone;
    private String email;
    private String avatar;
    private Double balance;
}
