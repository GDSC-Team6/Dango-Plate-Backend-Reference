package com.example.dango.review.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReviewReq {
    private String reviewContent;
    private String shopUid;
}
