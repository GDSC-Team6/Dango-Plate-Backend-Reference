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
    Long id;
    Long user_id;
    Long shop_id;
    String content;
    List<String> urls;
}
