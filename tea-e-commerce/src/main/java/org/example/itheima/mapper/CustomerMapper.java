package org.example.itheima.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.itheima.pojo.Customer;
import org.example.itheima.pojo.Order;

import java.util.List;

@Mapper
public interface CustomerMapper {
    @Insert("insert into customer(password,username) values(#{password},#{username})")
    void register(String password, String username);
    @Select("select * from customer where username=#{username}")
    Customer login(String username);
    @Select("select * from orders where customer_name=#{name}")
    List<Order> orderList(String name);
    void update(Customer customer);
}
