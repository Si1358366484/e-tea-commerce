package org.example.itheima.mapper;

import org.apache.ibatis.annotations.*;
import org.example.itheima.pojo.Order;
import org.example.itheima.pojo.OrderItem;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("select * from orders")
    List<Order> orderList();
    @Insert("insert into orders(order_reference,order_amounts,customer_name,state,shipping_address) " +
    "values(#{orderReference},#{orderAmounts},#{customerName},#{state},#{shippingAddress})")
    void addOrder(Order order);
    @Update("update orders set state=#{state} where id=#{id}")
    void updateOrder(Order order);
    @Select("select * from orders where order_reference=#{orderReference}")
    Order orderSearch(String orderReference);
    @Insert("insert into order_item(order_reference,quantity,name,price) " +
            "values(#{orderReference},#{quantity},#{name},#{price})")
    void addOrderItem(OrderItem oi);
    @Select("select * from order_item where order_reference=#{orderReference}")
    List<OrderItem> orderItemSearch(String orderReference);
}
