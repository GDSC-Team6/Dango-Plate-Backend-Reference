package com.example.dango.image.service;

import com.example.dango.image.entity.ReviewImage;
import com.example.dango.image.repository.ReviewImageRepository;
import com.example.dango.review.entity.Review;
import com.example.dango.review.repository.ReviewRepository;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
    private final ReviewRepository reviewRepository;

    public String uploadReviewImage(MultipartFile file, Review review) throws IOException {
        String fileName = UUID.randomUUID() + file.getOriginalFilename(); // Google Cloud Storage에 저장될 파일 이름
        String ext = file.getContentType(); // 파일의 형식 ex) JPG

        log.info("fileName: {}", fileName);
        log.debug("ext: {}", ext);
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(BUCKET_NAME, fileName)
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
                .imageName(fileName)
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

    public void deleteReviewImages(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."));
        List<ReviewImage> reviewImage = review.getReviewImages();
        for (ReviewImage image : reviewImage) {
            Blob blob = storage.get(BUCKET_NAME, image.getImageName());
            if (blob == null) {
                continue;
            }
            Storage.BlobSourceOption precondition = Storage.BlobSourceOption.generationMatch(blob.getGeneration());
            storage.delete(BUCKET_NAME, image.getImageName(), precondition);
        }
    }

    public String uploadTest(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String ext = file.getContentType(); // 파일의 형식 ex) JPG

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(BUCKET_NAME, uuid)
                        .setContentType(ext)
                        .build(),
                file.getBytes()
        );
        return blobInfo.getMediaLink();
    }
}
