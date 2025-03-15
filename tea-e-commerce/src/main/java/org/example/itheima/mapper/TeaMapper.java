package org.example.itheima.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.itheima.pojo.PageBean;
import org.example.itheima.pojo.Tea;

import java.util.List;

@Mapper
public interface TeaMapper {
    @Select("select * from tea")
    List<Tea> getTeaList();
    @Select("select * from tea where id=#{id}")
    Tea getTeaById(Integer id);

    List<Tea> teaList(String categoryName);
    @Delete("delete from tea where id=#{id}")
    void deleteTea(Integer id);
}
