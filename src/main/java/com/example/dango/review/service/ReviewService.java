package com.example.dango.review.service;

import com.example.dango.review.dto.ReviewReq;
import com.example.dango.review.entity.Review;
import com.example.dango.review.repository.ReviewRepository;
import com.example.dango.shop.entity.Shop;
import com.example.dango.shop.repository.ShopRepository;
import com.example.dango.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;

    public Review getReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            return review.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다.");
        }
    }

    public Review postReview(User user, ReviewReq reviewReq) {
        Shop shop = shopRepository.findByShopUid(reviewReq.getShop_uid()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다."));
        Review review = Review.builder()
                .user(user)
                .shop(shop)
                .reviewContent(reviewReq.getReview_content())
                .build();
        reviewRepository.save(review);
        return review;
    }
}
