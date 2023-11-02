package com.example.dango.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@Slf4j
public class KakaoWebClientService {

    @Value("${kakao.api-url}")
    private String kakaoApiUrl;

    @Value("${kakao.rest-api-key}")
    private String kakaoApiKey;

    private final WebClient webClient;

    public KakaoWebClientService() {
        this.webClient = WebClient.builder()
                .baseUrl(kakaoApiUrl)
                .defaultHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .defaultHeader("Authorization", "KakaoAK " + kakaoApiKey)
                .build();
    }

    public Map<String, Object> get(MultiValueMap<String, String> query) {
        // api 요청
        Map<String, Object> response =
            webClient
                .get()
                .uri(uriBuilder ->
                    uriBuilder
                        .queryParams(query)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // 결과 확인
        log.info(response.toString());
        return response;
    }
}