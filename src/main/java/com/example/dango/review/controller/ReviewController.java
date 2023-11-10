package com.example.dango.review.controller;

import com.example.dango.global.entity.ApiResponse;
import com.example.dango.image.service.ImageService;
import com.example.dango.review.dto.ReviewReq;
import com.example.dango.review.dto.ReviewRes;
import com.example.dango.review.entity.Review;
import com.example.dango.review.service.ReviewService;
import com.example.dango.user.entity.User;
import com.example.dango.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@Api(tags={"03.review"})
@Slf4j(topic = "REVIEW_CONTROLLER")
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final ImageService imageService;

    @GetMapping("")
    public ApiResponse<Review> reviewGet(@RequestParam Long reviewId) {
        return new ApiResponse<>(reviewService.getReview(reviewId));
    }

    @PostMapping("")
    public ApiResponse<ReviewRes> reviewPost(ReviewReq reviewReq) {
        User loginUser;
        try {
            loginUser = userService.findNowLoginUser();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        try {
            log.info("reviewReq: {}", reviewReq);
            Review review = reviewService.postReview(loginUser, reviewReq);
            log.info("review: {}", review);
            List<String> urls = imageService.uploadReviewImages(reviewReq.getImages(), review);
            log.info("urls: {}", urls);
            return new ApiResponse<>(ReviewRes.builder()
                    .review(review)
                    .urls(urls)
                    .build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "리뷰를 작성할 수 없습니다.");
        }
    }
}
