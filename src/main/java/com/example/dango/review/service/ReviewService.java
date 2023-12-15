package com.example.dango.review.service;

import com.example.dango.image.entity.ReviewImage;
import com.example.dango.image.repository.ReviewImageRepository;
import com.example.dango.image.service.S3Service;
import com.example.dango.review.dto.ReviewReq;
import com.example.dango.review.dto.ReviewRes;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final ReviewImageRepository reviewImageRepository;
    private final ShopRepository shopRepository;
    private final S3Service s3Service;


    public ReviewRes getReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        if (review.isPresent()) {
            List<ReviewImage> images = reviewImageRepository.findReviewImageByReview(review.get());
            List<String> urls = new ArrayList<>();
            for (ReviewImage reviewImage : images) {
                urls.add(reviewImage.getUrl());
            }

            return ReviewRes.builder()
                    .id(review.get().getId())
                    .user_id(review.get().getUser().getId())
                    .shop_id(review.get().getShop().getId())
                    .content(review.get().getReviewContent())
                    .urls(urls)
                    .build();

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다.");
        }

    }

    public ReviewRes postReview(User user, ReviewReq reviewReq, List<MultipartFile> images) {
        Shop shop = shopRepository.findByShopUid(reviewReq.getShop_uid()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다."));

        Review review = Review.builder()
                .user(user)
                .shop(shop)
                .reviewContent(reviewReq.getReview_content())
                .grade(reviewReq.getGrade())
                .build();
        reviewRepository.save(review);

        List<String> urls = new ArrayList<>();
        for (MultipartFile file : images) {
            urls.add(s3Service.uploadImage("board", Long.toString(review.getId()), file));
        }


        return ReviewRes.builder()
                .id(review.getId())
                .user_id(review.getUser().getId())
                .shop_id(review.getShop().getId())
                .content(review.getReviewContent())
                .urls(urls)
                .build();
    }

    public void deleteReview(User loginUser, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."));
        if (review.getUser().getKakaoId() != loginUser.getKakaoId()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
        }
        List<ReviewImage> images = reviewImageRepository.findReviewImageByReview(review);
        for(ReviewImage image : images){
            reviewImageRepository.delete(image);
        }
        reviewRepository.delete(review);
    }
}
