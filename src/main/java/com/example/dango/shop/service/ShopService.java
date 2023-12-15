package com.example.dango.shop.service;

import com.example.dango.global.entity.ApiResponse;
import com.example.dango.image.entity.ShopImage;
import com.example.dango.image.repository.ShopImageRepository;
import com.example.dango.review.entity.Review;
import com.example.dango.review.repository.ReviewRepository;
import com.example.dango.review.service.ReviewService;
import com.example.dango.shop.dto.ShopRes;
import com.example.dango.shop.entity.Shop;
import com.example.dango.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    private final ShopImageRepository shopImageRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    public ShopRes getShop(Long shopUid) {
        Optional<Shop> shop = shopRepository.findByShopUid(shopUid);
        if (shop.isPresent()) {

            List<Long> reviewIds = new ArrayList<>();
            for (Review review : reviewRepository.findReviewByShop(shop.get())) {
                reviewIds.add(review.getId());
            }
            List<String> imageUrls = new ArrayList<>();
            for (ShopImage image : shopImageRepository.findShopImageByShop(shop.get())) {
                imageUrls.add(image.getUrl());
            }

            return ShopRes.builder()
                    .id(shop.get().getId())
                    .shopUid(shop.get().getShopUid())
                    .reviewIds(reviewIds)
                    .imageUrls(imageUrls)
                    .gradeAvg(reviewService.getGradeAvg(shop.get()))
                    .build();

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다.");
        }
    }

}
