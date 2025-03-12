package org.example.itheima.mapper;

import org.apache.ibatis.annotations.*;
import org.example.itheima.pojo.User;

@Mapper
public interface UserMapper {
    //根据用户名查寻用户
    @Select("select * from user where  username = #{username}")
    User findByUsername(String username);

    //添加用户
    @Insert("insert into user(username,password,create_time,update_time)" +
            " values (#{username},#{password},now(),now())")
    void add(String username, String password);
    //更新用户
    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);
    //更新头像
    @Update("update user set user_pic=#{userPic},update_time=now() where id=#{id}")
    void updateAvatar(@Param("userPic") String avatarUrl,@Param("id") Integer id);
    //更新密码
    @Update("update user set password=#{password},update_time=now() where id=#{id}")
    void updatePwd(String password,Integer id);
}
