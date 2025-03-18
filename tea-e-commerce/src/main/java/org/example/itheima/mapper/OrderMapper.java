package org.example.itheima.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.itheima.pojo.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("select * from orders")
    List<Order> orderList();
    @Insert("insert into orders(order_reference,tea_number,tea_name,order_amounts,customer_name,state) " +
    "values(#{orderReference},#{teaNumber},#{teaName},#{orderAmounts},#{customerName},#{state})")
    void addOrder(Order order);
}
