package com.example.dango.favorite.controller;

import com.example.dango.favorite.dto.FavoriteRes;
import com.example.dango.favorite.entity.Favorite;
import com.example.dango.favorite.service.FavoriteService;
import com.example.dango.global.entity.ApiResponse;
import com.example.dango.user.entity.User;
import com.example.dango.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Api(tags={"05.favorite"})
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final UserService userService;
    @GetMapping("/me")
    public ApiResponse<FavoriteRes> getFavorites() {
        User loginUser;
        try {
            loginUser = userService.findNowLoginUser();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        Favorite favorite = favoriteService.getFavorite(loginUser.getId());
        return new ApiResponse<>(FavoriteRes.builder()
                .id(favorite.getId())
                .userId(favorite.getUser().getId())
                .shopId(favorite.getShop().getId())
                .build());
    }

    @PostMapping("/{shopId}")
    public ApiResponse<FavoriteRes> postFavorite(@PathVariable Long shopId) {
        User loginUser;
        try {
            loginUser = userService.findNowLoginUser();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        Favorite favorite = favoriteService.postFavorite(loginUser, shopId);
        return new ApiResponse<>(FavoriteRes.builder()
                .id(favorite.getId())
                .userId(favorite.getUser().getId())
                .shopId(favorite.getShop().getId())
                .build());
    }

    @DeleteMapping("/{favoriteId}")
    public ApiResponse<?> deleteFavorite(@PathVariable Long favoriteId) {
        try {
            userService.findNowLoginUser();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        favoriteService.deleteFavorite(favoriteId);
        return new ApiResponse<>("즐겨찾기 삭제 완료");
    }
}
