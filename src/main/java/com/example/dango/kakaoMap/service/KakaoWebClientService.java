package com.example.dango.kakaoMap.service;

import com.example.dango.shop.entity.Shop;
import com.example.dango.shop.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j(topic = "KakaoWebClientService")
public class KakaoWebClientService {

    @Value("${kakao.api-url}")
    private String kakaoApiUrl;

    @Value("${kakao.rest-api-key}")
    private String kakaoApiKey;

    private final ShopRepository shopRepository;

    private final WebClient webClient;

    public KakaoWebClientService(ShopRepository shopRepository) {
        this.webClient = WebClient.create();
        this.shopRepository = shopRepository;
    }

    public Map<?, ?> get(String query, String x, String y, String radius) {
        // api 요청
        String url = kakaoApiUrl + "/v2/local/search/keyword.json?";
        if (x != null && !x.isEmpty()) {
            url += "x=" + x + "&";
        }
        if (y != null && !y.isEmpty()) {
            url += "y=" + y + "&";
        }
        if (radius != null && !radius.isEmpty()) {
            url += "radius=" + radius + "&";
        }

        Map<?, ?> response =
            webClient
                .get()
                .uri(url + "query=" + query)
                .header("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .header("Authorization", "KakaoAK " + kakaoApiKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List<?> document = Objects.requireNonNull(response).get("documents") != null ? (List<?>) response.get("documents") : null;
        if (document != null) {
            for (Object o : document) {
                Map<String, String> map = (Map<String, String>) o;
                Long shopUid = Long.parseLong(map.get("id"));
                if (shopRepository.findByShopUid(shopUid).isPresent()) {
                    continue;
                }
                Shop shop = Shop.builder()
                        .shopUid(shopUid)
                        .build();
                shopRepository.save(shop);
            }
        }
        // 결과 확인
        log.info(Objects.requireNonNull(response).toString());
        return response;
    }
}