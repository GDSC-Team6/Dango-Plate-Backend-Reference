package com.example.dango.kakaoMap.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SearchReq {
    private String x;
    private String y;
    private String radius;
    private String query;
}
