package com.example.dango.image.repository;

import com.example.dango.image.entity.ReviewImage;
import com.example.dango.image.entity.ShopImage;
import com.example.dango.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopImageRepository extends JpaRepository<ShopImage, Long> {

    List<ShopImage> findShopImageByShop(Shop shop);
}
