package com.example.dango.image.service;

import com.example.dango.image.entity.ReviewImage;
import com.example.dango.image.repository.ReviewImageRepository;
import com.example.dango.review.entity.Review;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "IMAGE_SERVICE")
@Transactional
public class ImageService {
    private final Storage storage;
    private static final String BUCKET_NAME = "dango-image";
    private final ReviewImageRepository reviewImageRepository;

    public String uploadReviewImage(MultipartFile file, Review review) throws IOException {
        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String ext = file.getContentType(); // 파일의 형식 ex) JPG

        log.info("uuid: {}", uuid);
        log.debug("ext: {}", ext);
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(BUCKET_NAME, uuid)
                        .setContentType(ext)
                        .build(),
                file.getBytes()
        );
        log.info("blobInfo: {}", blobInfo);
        log.debug("blobInfo: {}", blobInfo);
        String url = blobInfo.getMediaLink();
        log.info("url: {}", url);
        log.debug("url: {}", url);
        ReviewImage reviewImage = ReviewImage.builder()
                .review(review)
                .url(url)
                .build();
        log.info("reviewImage: {}", reviewImage);
        log.debug("reviewImage: {}", reviewImage);
        reviewImageRepository.save(reviewImage);
        return url;
    }

    public List<String> uploadReviewImages(List<MultipartFile> files, Review review) throws IOException {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            urls.add(uploadReviewImage(file, review));
        }
        return urls;
    }
}
