package org.example.itheima.service;

import org.example.itheima.pojo.Review;
import org.example.itheima.pojo.ReviewPage;

import java.util.List;

public interface ReviewService {
    void createReview(Review review);

    ReviewPage<Review> reviewList(Integer pageNum, Integer pageSize);
}
