package org.example.itheima.mapper;

import org.apache.ibatis.annotations.*;
import org.example.itheima.pojo.Review;

import java.util.List;

@Mapper
public interface ReviewMapper {
    @Insert("insert into review(customer_name,tea_name,grade,content,review_time,state) " +
            "values(#{customerName},#{teaName},#{grade},#{content},#{reviewTime},#{state})")
    void createReview(Review review);
    @Select("SELECT * FROM review ORDER BY review_time DESC, id DESC LIMIT #{offset}, #{pageSize}")
    List<Review> getReviewsByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);
    @Select("SELECT COUNT(*) FROM review")
    int getTotalReviews();
    @Delete("delete from review where id=#{id}")
    void delete(Integer id);
}
