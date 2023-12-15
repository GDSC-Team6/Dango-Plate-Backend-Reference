package com.example.dango.review.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReviewReq {
    private String review_content;
    private Long shop_uid;
    private double grade;
}
