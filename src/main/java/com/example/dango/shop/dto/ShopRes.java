package com.example.dango.shop.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopRes {
    Long id;
    Long shopUid;
    List<String> imageUrls;
    List<Long> reviewIds;
}
