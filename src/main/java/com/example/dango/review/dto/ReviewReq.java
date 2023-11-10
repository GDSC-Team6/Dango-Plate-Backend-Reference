package com.example.dango.review.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReviewReq {
    private String review_content;
    private String shop_uid;
    private List<MultipartFile> images;
}
