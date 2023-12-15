package com.example.dango.shop.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopRes {
    private Long id;
    private Long shopUid;
    private List<String> imageUrls;
    private List<Long> reviewIds;
    private double gradeAvg;
}
