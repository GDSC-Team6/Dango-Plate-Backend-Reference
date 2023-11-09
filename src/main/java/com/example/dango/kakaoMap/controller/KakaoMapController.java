package com.example.dango.kakaoMap.controller;

import com.example.dango.kakaoMap.service.KakaoWebClientService;
import com.example.dango.global.entity.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Api(tags={"02.map"})
@RequiredArgsConstructor
@RequestMapping("/map")
public class KakaoMapController {
    private final KakaoWebClientService kakaoWebClientService;
    @ApiOperation(value = "카카오 맵 검색", notes = "카카오 맵 검색")
    @GetMapping("/search")
    public ApiResponse<Map<?, ?>> search(@RequestParam String query) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("query", query);
        return new ApiResponse<>(kakaoWebClientService.get(params));
    }
}
