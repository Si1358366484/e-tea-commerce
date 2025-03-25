package org.example.itheima.controller;

import org.example.itheima.pojo.Result;
import org.example.itheima.pojo.Review;
import org.example.itheima.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @PostMapping("/createReview")
    public Result createReview(@RequestBody @Validated Review review) {
        reviewService.createReview(review);
        return Result.success(review);
    }
    //下面是管理员端
    @GetMapping("/admin/reviewList")
    public Result<List<Review>> reviewList() {
        List<Review> reviewList = reviewService.reviewList();
        return Result.success(reviewList);
    }
}
