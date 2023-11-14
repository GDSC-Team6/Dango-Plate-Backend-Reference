package com.example.dango.favorite.service;

import com.example.dango.favorite.entity.Favorite;
import com.example.dango.favorite.repository.FavoriteRepository;
import com.example.dango.shop.entity.Shop;
import com.example.dango.shop.repository.ShopRepository;
import com.example.dango.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final ShopRepository shopRepository;

    public Favorite getFavorite(Long userId) {
        return favoriteRepository.findByUserId(userId).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "즐겨찾기를 찾을 수 없습니다."));
    }

    public Favorite postFavorite(User user, Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다."));
        Favorite favorite = Favorite.builder()
                .user(user)
                .shop(shop)
                .build();
        return favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Long favoriteId) {
        try {
            favoriteRepository.deleteById(favoriteId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "즐겨찾기를 찾을 수 없습니다.");
        }
    }
}
