package org.example.itheima.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.itheima.pojo.Admin;

@Mapper
public interface AdminMapper {
    @Select("select * from admin where username=#{username}")
    Admin login(String username);
}
