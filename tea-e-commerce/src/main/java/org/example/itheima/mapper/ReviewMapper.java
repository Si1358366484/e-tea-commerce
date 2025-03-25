package org.example.itheima.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.itheima.pojo.Review;

import java.util.List;

@Mapper
public interface ReviewMapper {
    @Insert("insert into review(customer_name,tea_name,grade,content,review_time,state) " +
            "values(#{customerName},#{teaName},#{grade},#{content},#{reviewTime},#{state})")
    void createReview(Review review);
    @Select("select * from review")
    List<Review> reviewList();
}
