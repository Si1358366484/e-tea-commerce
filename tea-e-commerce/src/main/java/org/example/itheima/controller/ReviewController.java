package org.example.itheima.controller;

import org.example.itheima.dto.ReviewDTO;
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
    @PostMapping("/createReview/{teaId}")
    public Result createReview(@PathVariable("teaId")Integer id, @RequestBody @Validated ReviewDTO review) {
        reviewService.createReview(review,id);
        return Result.success(review);
    }
    @GetMapping("/comments/{teaId}")
    public Result<List<ReviewDTO>> getCommentsById(@PathVariable("teaId") Integer id) {
        List<ReviewDTO> comments = reviewService.getCommentsById(id);
        return Result.success(comments);
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
        reviewService.delete(id);
        return Result.success();
    }
    @PutMapping("/admin/approveReview/{id}")
    public Result approveReview(@PathVariable("id") Integer id) {
        reviewService.approveReview(id);
        return Result.success();
    }
}
