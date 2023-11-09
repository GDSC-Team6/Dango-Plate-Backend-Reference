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
    Review review;
    List<String> urls;
}
