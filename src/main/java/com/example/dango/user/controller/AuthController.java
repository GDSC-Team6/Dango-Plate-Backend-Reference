package com.example.dango.user.controller;

import com.example.dango.global.entity.ApiResponse;
import com.example.dango.global.exception.BadRequestException;
import com.example.dango.user.dto.TokenRes;
import com.example.dango.user.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags={"01.user"})
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    /**
     * 카카오 소셜로그인
     * https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=b7efcdeef9cf0be8991e8d2fdc1dc2ba&redirect_uri=http://localhost:8080/oauth/kakao
     * https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=b7efcdeef9cf0be8991e8d2fdc1dc2ba&redirect_uri=http://35.216.0.111:8080/oauth/kakao
     * */
    @ApiOperation(value = "카카오 서버의 액세스 토큰(서버용)",
            notes = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=b7efcdeef9cf0be8991e8d2fdc1dc2ba&redirect_uri=http://localhost:8080/oauth/kakao\n" +
                    "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=b7efcdeef9cf0be8991e8d2fdc1dc2ba&redirect_uri=dangoplate://oauth\n")
    @GetMapping("/kakao")
    public ApiResponse<String> getAccessTokenKakao(@RequestParam String code) {
        String accessTokenFromSocial = authService.getKakaoAccessToken(code);
        return new ApiResponse<>(accessTokenFromSocial);
    }

    @ApiOperation(value = "카카오 소셜 로그인")
    @GetMapping("/kakao/login")
    public ApiResponse<TokenRes> kakaoLogin(@RequestParam String accessTokenFromSocial) {
        //String accessTokenFromSocial = authService.getKakaoAccessToken(code);
        TokenRes tokenRes = authService.createAndLoginKakaoUser(accessTokenFromSocial);
        if(tokenRes == null)
            throw new BadRequestException("사용자 정보가 없습니다");
        return new ApiResponse<>(tokenRes);
    }


}
