package org.example.itheima.service.Impl;

import org.example.itheima.mapper.ReviewMapper;
import org.example.itheima.pojo.OrderStatus;
import org.example.itheima.pojo.Review;
import org.example.itheima.pojo.ReviewPage;
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
    public ReviewPage<Review> reviewList(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Review> reviews = reviewMapper.getReviewsByPage(offset, pageSize);
        int total = reviewMapper.getTotalReviews(); // 总数据量
        boolean hasMore = (pageNum * pageSize) < total;

        ReviewPage<Review> page = new ReviewPage<>();
        page.setComments(reviews);
        page.setHasMore(hasMore);
        return page;
    }

    @Override
    public void delete(Integer id) {
        reviewMapper.delete(id);
    }
}
