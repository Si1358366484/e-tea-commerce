package org.example.itheima.service.Impl;

import org.example.itheima.dto.ReviewDTO;
import org.example.itheima.mapper.CustomerMapper;
import org.example.itheima.mapper.ReviewMapper;
import org.example.itheima.mapper.TeaMapper;
import org.example.itheima.pojo.*;
import org.example.itheima.service.ReviewService;
import org.example.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TeaMapper teaMapper;
    @Override
    public void createReview(ReviewDTO review, Integer id) {
        Review review1 = new Review();
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userid = (Integer) map.get("id");
        review1.setCustomerId(userid);
        review1.setReviewTime(LocalDateTime.now());
        review1.setState(ReviewState.待审核.getDescription());
        String teaName = teaMapper.getTeaById(id).getName();
        review1.setTeaName(teaName);
        review1.setTeaId(id);
        review1.setGrade(review.getRating());
        review1.setContent(review.getContent());
        review1.setCustomerName(review.getUsername());
        System.out.println(review1);
        reviewMapper.createReview(review1);
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

    @Override
    public List<ReviewDTO> getCommentsById(Integer id) {
        List<ReviewDTO> comments = new ArrayList<>();

        List<Review> reviews = reviewMapper.getCommentsById(id);
        for (Review review : reviews) {
            ReviewDTO comment = new ReviewDTO();
            Integer customerId = review.getCustomerId();
            Customer customer = customerMapper.queryInfoById(customerId);
            comment.setAvatar(customer.getAvatar());
            comment.setUsername(customer.getName());
            comment.setContent(review.getContent());
            comment.setRating(review.getGrade());
            comment.setTime(review.getReviewTime());
            comments.add(comment);
        }
        return comments;
    }

    @Override
    public void approveReview(Integer id) {
        ReviewState state = ReviewState.已通过;
        reviewMapper.approveReview(id, state.getDescription());
    }
}
