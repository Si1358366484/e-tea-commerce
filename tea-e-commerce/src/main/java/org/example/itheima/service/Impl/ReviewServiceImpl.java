package org.example.itheima.service.Impl;

import org.example.itheima.mapper.ReviewMapper;
import org.example.itheima.pojo.OrderStatus;
import org.example.itheima.pojo.Review;
import org.example.itheima.pojo.ReviewState;
import org.example.itheima.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;
    @Override
    public void createReview(Review review) {
        review.setReviewTime(LocalDateTime.now());
        review.setState(ReviewState.待审核.getDescription());
        reviewMapper.createReview(review);
    }

    @Override
    public List<Review> reviewList() {
        return reviewMapper.reviewList();
    }
}
