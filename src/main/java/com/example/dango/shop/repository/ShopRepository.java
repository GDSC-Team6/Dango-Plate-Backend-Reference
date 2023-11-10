package com.example.dango.shop.repository;

import com.example.dango.shop.entity.Shop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    public Optional<Shop> findByShopUid(Long shopUid);
}
