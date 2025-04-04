package org.example.itheima.mapper;

import org.apache.ibatis.annotations.*;
import org.example.itheima.dto.PasswordUpdateDTO;
import org.example.itheima.pojo.Customer;
import org.example.itheima.pojo.CustomerAddress;
import org.example.itheima.pojo.Order;

import java.util.List;

@Mapper
public interface CustomerMapper {
    @Insert("insert into customer(password,username) values(#{password},#{username})")
    void register(String password, String username);
    @Select("select * from customer where username=#{username}")
    Customer login(String username);
    @Select("select * from orders where customer_id=#{id}")
    List<Order> orderList(Integer id);
    @Select("select * from customer where id=#{id}")
    Customer queryInfoById(Integer id);
    @Select("select * from customer_addresses where id=#{id}")
    List<CustomerAddress> addressList(Integer id);

    void updateInfo(Customer customer);
    @Update("update customer set password=#{newPassword} where id=#{id}")
    void updatePassword(PasswordUpdateDTO dto);
    @Insert("insert into customer_addresses(id,name,phone,address) values(#{id},#{name},#{phone},#{address})")
    void addAddress(CustomerAddress customerAddress);
    @Update("update customer_addresses set name=#{name},phone=#{phone},address=#{address} where address_id=#{addressId}")
    void updateAddress(CustomerAddress customerAddress);
    @Delete("delete from customer_addresses where address_id=#{addressId}")
    void deleteAddress(Long id, Long addressId);
    @Select("SELECT * FROM orders WHERE customer_id=#{id} ORDER BY create_time DESC LIMIT 1")
    List<Order> newOrder(Integer id);
    @Update("update customer set balance = balance +#{balance} where id=#{userId}")
    void updateBalance(Integer userId,Double balance);
}
