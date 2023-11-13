package com.example.dango.kakaoMap.controller;

import com.example.dango.kakaoMap.dto.SearchReq;
import com.example.dango.kakaoMap.service.KakaoWebClientService;
import com.example.dango.global.entity.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@Slf4j
@Api(tags={"02.map"})
@RequiredArgsConstructor
@RequestMapping("/map")
public class KakaoMapController {
    private static final String KeywordEndPoint = "/v2/local/search/keyword.json";

    private final KakaoWebClientService kakaoWebClientService;
    @ApiOperation(value = "카카오 맵 검색", notes = "키워드는 필수이고 x, y, radius는 선택항목입니다." +
            "x, y, radius를 모두 입력하면 중심좌표를 기준으로 반경 내에 있는 장소를 검색합니다. x, y, radius를 입력하지 않으면 키워드를 기준으로 검색합니다." +
            "x, y는 모두 입력하거나 모두 입력하지 않아야 합니다." +
            "x는 경도, y는 위도이며 radius 단위는 미터입니다.(최소:0, 최대:20000)\n")
    @GetMapping("/search")
    public ApiResponse<Map<?, ?>> search(@RequestParam(required = false) String x,
                                          @RequestParam(required = false) String y,
                                          @RequestParam(required = false) String radius,
                                          @RequestParam String query) {
        if (query == null || query.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "검색어를 입력해주세요.");
        }
        try {
            Map<?,?> map = kakaoWebClientService.get(query, x, y, radius);
            return new ApiResponse<>(map);
        } catch (Exception e) {
            log.error("카카오 맵 검색 실패" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "카카오 맵 검색 실패");
        }
    }
}
