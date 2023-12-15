package com.example.dango.review.dto;

import com.example.dango.review.entity.Review;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRes {
    private Long id;
    private Long shop_id;
    private String content;
    private List<String> urls;
    private double grade;

    private Long user_id;
    private String name;
}
