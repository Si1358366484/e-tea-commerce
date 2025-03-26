package org.example.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPage <T>{
    //private Long total;//总条数
    private List<T> comments;//当前页数据集合
    private Boolean hasMore;//是否有下一页
}
