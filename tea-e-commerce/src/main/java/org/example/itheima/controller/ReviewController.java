package org.example.itheima.controller;

import org.example.itheima.pojo.Result;
import org.example.itheima.pojo.Review;
import org.example.itheima.pojo.ReviewPage;
import org.example.itheima.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Result<ReviewPage<Review>> reviewList(Integer pageNum, Integer pageSize) {
        //System.out.println(pageNum + " " + pageSize);
        ReviewPage<Review> reviewList = reviewService.reviewList(pageNum,pageSize);
        return Result.success(reviewList);
    }
    @DeleteMapping("/admin/deleteReview/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        System.out.println(id);
        reviewService.delete(id);
        return Result.success();
    }
}
