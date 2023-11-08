package com.example.dango.review.controller;

import com.example.dango.review.dto.ReviewReq;
import com.example.dango.review.service.ReviewService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags={"03.review"})
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    @GetMapping("/")
    public String reviewGet(@RequestParam Long reviewId) {
        return reviewService.getReview(reviewId);
    }

    @PostMapping("/")
    public String reviewPost(@RequestParam Long reviewId, @RequestBody ReviewReq reviewReq) {
        return reviewService.postReview(reviewId, reviewReq);
    }
}
