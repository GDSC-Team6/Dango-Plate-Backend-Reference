package com.example.dango.favorite.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRes {
    private Long id;
    private Long userId;
    private Long shopId;
}
