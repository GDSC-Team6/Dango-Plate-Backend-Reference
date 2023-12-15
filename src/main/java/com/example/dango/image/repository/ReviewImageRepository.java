package com.example.dango.image.repository;

import com.example.dango.image.entity.ReviewImage;
import com.example.dango.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

    List<ReviewImage> findReviewImageByReview(Review review);

}
