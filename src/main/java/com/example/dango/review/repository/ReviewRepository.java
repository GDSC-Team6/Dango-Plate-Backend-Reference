package com.example.dango.review.repository;

import com.example.dango.review.entity.Review;
import com.example.dango.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewByShop(Shop shop);

}
