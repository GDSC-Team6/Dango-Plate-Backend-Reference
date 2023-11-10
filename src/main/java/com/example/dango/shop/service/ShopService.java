package com.example.dango.shop.service;

import com.example.dango.shop.entity.Shop;
import com.example.dango.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    public Shop getShop(Long shopId) {
        Optional<Shop> shop = shopRepository.findById(shopId);
        if (shop.isPresent()) {
            return shop.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다.");
        }
    }

}
