package org.example.itheima.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.itheima.pojo.Admin;

@Mapper
public interface AdminMapper {
    @Select("select * from admin where username=#{username}")
    Admin login(String username);
    @Select("select * from admin where username=#{username}")
    Admin findByName(String name);
    @Update("update admin set password=#{newPwd} where id=#{id}")
    void updatePwd(String newPwd,Integer id);
    @Update("update admin set admin_pic=#{adminPic} where id=#{id}")
    void updateAvatar(@Param("adminPic") String avatarUrl,@Param("id") Integer id);
}
