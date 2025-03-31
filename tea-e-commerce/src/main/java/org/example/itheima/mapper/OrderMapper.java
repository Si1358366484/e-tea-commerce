package org.example.itheima.mapper;

import org.apache.ibatis.annotations.*;
import org.example.itheima.pojo.Order;
import org.example.itheima.pojo.OrderItem;
import org.example.itheima.pojo.Tea;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("select * from orders")
    List<Order> orderList();
    @Insert("insert into orders(order_reference,order_amounts,customer_name,state,create_time) " +
    "values(#{orderReference},#{orderAmounts},#{customerName},#{state},#{createTime})")
    void addOrder(Order order);
    @Update("update orders set state=#{state} where order_reference=#{orderReference}")
    void updateOrder(Order order);
    @Select("select * from orders where order_reference=#{orderReference}")
    Order orderSearch(String orderReference);
    @Insert("insert into order_item(order_reference,quantity,name,price) " +
            "values(#{orderReference},#{quantity},#{name},#{price})")
    void addOrderItem(OrderItem oi);
    @Select("select * from order_item where order_reference=#{orderReference}")
    List<OrderItem> orderItemSearch(String orderReference);
    @Update("update orders set shipping_address=#{address},customer_name=#{name},phone=#{phone} where order_reference=#{orderId}")
    void updateAddress(String orderId, String address,String name,String phone);
    List<Order> orderQueryList(@Param("status") String status,@Param("username") String username);
    @Select("select * from tea where name = #{name}")
    List<Tea> teaSearch(String name);
}
