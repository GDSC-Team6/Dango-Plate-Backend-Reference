package com.example.dango.review.controller;

import com.example.dango.review.dto.ReviewReq;
import com.example.dango.review.entity.Review;
import com.example.dango.review.service.ReviewService;
import com.example.dango.user.entity.User;
import com.example.dango.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Api(tags={"03.review"})
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    @GetMapping("/")
    public Review reviewGet(@RequestParam Long reviewId) {
        return reviewService.getReview(reviewId);
    }

    @PostMapping("/")
    public Review reviewPost(@RequestBody ReviewReq reviewReq) {
        User loginUser;
        try {
            loginUser = userService.findNowLoginUser();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        return reviewService.postReview(loginUser, reviewReq);
    }
}
