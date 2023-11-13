package com.example.dango.image.repository;

import com.example.dango.image.entity.ReviewImage;
import com.example.dango.image.entity.ShopImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopImageRepository extends JpaRepository<ShopImage, Long> {
}
