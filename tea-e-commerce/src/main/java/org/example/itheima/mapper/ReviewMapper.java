package org.example.itheima.mapper;

import org.apache.ibatis.annotations.*;
import org.example.itheima.dto.ReviewDTO;
import org.example.itheima.pojo.Review;

import java.util.List;

@Mapper
public interface ReviewMapper {
    @Insert("insert into review(customer_name,tea_name,grade,content,review_time,state,tea_id,customer_id) " +
            "values(#{customerName},#{teaName},#{grade},#{content},#{reviewTime},#{state},#{teaId},#{customerId})")
    void createReview(Review review);
    @Select("SELECT * FROM review ORDER BY review_time DESC, id DESC LIMIT #{offset}, #{pageSize}")
    List<Review> getReviewsByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);
    @Select("SELECT COUNT(*) FROM review")
    int getTotalReviews();
    @Delete("delete from review where id=#{id}")
    void delete(Integer id);
    @Select("select * from review where tea_id=#{id} AND state = '已通过'")
    List<Review> getCommentsById(Integer id);
    @Update("update review set state=#{description} where id=#{id}")
    void approveReview(Integer id, String description);
}
