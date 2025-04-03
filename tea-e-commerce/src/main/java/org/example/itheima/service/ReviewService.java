package org.example.itheima.service;

import org.example.itheima.dto.ReviewDTO;
import org.example.itheima.pojo.Review;
import org.example.itheima.pojo.ReviewPage;

import java.util.List;

public interface ReviewService {
    void createReview(ReviewDTO review, Integer id);

    ReviewPage<Review> reviewList(Integer pageNum, Integer pageSize);

    void delete(Integer id);

    List<ReviewDTO> getCommentsById(Integer id);

    void approveReview(Integer id);
}
