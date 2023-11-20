package com.example.dango.shop.controller;

import com.example.dango.global.entity.ApiResponse;
import com.example.dango.image.entity.ShopImage;
import com.example.dango.review.entity.Review;
import com.example.dango.shop.dto.ShopRes;
import com.example.dango.shop.entity.Shop;
import com.example.dango.shop.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Api(tags={"04.shop"})
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    @GetMapping("")
    @Operation(summary = "가게 정보 가져오기")
    public ApiResponse<ShopRes> shopGet(@RequestParam Long shopUid) {
        Shop shop = shopService.getShop(shopUid);
        List<Long> reviewIds = new ArrayList<>();
        for (Review review : shop.getReviews()) {
            reviewIds.add(review.getId());
        }
        List<String> imageUrls = new ArrayList<>();
        for (ShopImage image : shop.getShopImages()) {
            imageUrls.add(image.getUrl());
        }
        return new ApiResponse<>(ShopRes.builder()
                .id(shop.getId())
                .shopUid(shop.getShopUid())
                .reviewIds(reviewIds)
                .imageUrls(imageUrls)
                .build());
    }
}
