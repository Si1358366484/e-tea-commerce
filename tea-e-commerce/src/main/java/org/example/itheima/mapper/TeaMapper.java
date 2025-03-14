package org.example.itheima.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.itheima.pojo.Tea;

import java.util.List;

@Mapper
public interface TeaMapper {
    @Select("select * from tea")
    List<Tea> getTeaList();
    @Select("select * from tea where id=#{id}")
    Tea getTeaById(Integer id);
}
