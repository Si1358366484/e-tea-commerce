package org.example.itheima.mapper;

import org.apache.ibatis.annotations.*;
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
    @Insert("insert into tea(category,name,description,origin,price,image) " +
            "values(#{category},#{name},#{description},#{origin},#{price},#{image})")
    void addTea(Tea tea);
    @Update("update tea set category=#{category},name=#{name},description=#{description}," +
            "origin=#{origin},price=#{price},image=#{image} where id=#{id}")
    void updateTea(Tea tea);

    List<Tea> search(String name);
}
