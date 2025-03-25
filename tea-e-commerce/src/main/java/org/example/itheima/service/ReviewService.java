package org.example.itheima.service;

import org.example.itheima.pojo.Review;

import java.util.List;

public interface ReviewService {
    void createReview(Review review);

    List<Review> reviewList();
}
