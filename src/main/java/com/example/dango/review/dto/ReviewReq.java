package com.example.dango.review.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReviewReq {
    private String review_content;
    private String shop_uid;
}
