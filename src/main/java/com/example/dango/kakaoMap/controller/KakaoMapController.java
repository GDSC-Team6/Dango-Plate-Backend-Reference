package com.example.dango.kakaoMap.controller;

import com.example.dango.kakaoMap.service.KakaoWebClientService;
import com.example.dango.global.entity.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@Slf4j
@Api(tags={"02.map"})
@RequiredArgsConstructor
@RequestMapping("/map")
public class KakaoMapController {
    private final KakaoWebClientService kakaoWebClientService;
    @ApiOperation(value = "카카오 맵 검색", notes = "카카오 맵 검색")
    @GetMapping("/search")
    public ApiResponse<Map<?, ?>> search(@RequestParam String query) {
        if (query == null || query.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "검색어를 입력해주세요.");
        }
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("query", query);
        Map<?,?> map;
        try {
            map = kakaoWebClientService.get(params);
        } catch (Exception e) {
            log.error("카카오 맵 검색 실패" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 맵 검색 실패");
        }
        return new ApiResponse<>(map);
    }
}
