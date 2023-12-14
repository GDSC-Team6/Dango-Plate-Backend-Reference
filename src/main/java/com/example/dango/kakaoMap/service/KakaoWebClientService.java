package com.example.dango.kakaoMap.service;

import com.example.dango.shop.entity.Shop;
import com.example.dango.shop.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j(topic = "KakaoWebClientService")
public class KakaoWebClientService {
    private final String kakaoApiUrl;
    private final String kakaoApiKey;

    private final ShopRepository shopRepository;

    private final WebClient webClient;

    public KakaoWebClientService(ShopRepository shopRepository,
                                 @Value("${kakao.api-url}") String kakaoApiUrl,
                                 @Value("${kakao.rest-api-key}") String kakaoApiKey) {
        this.webClient = WebClient.create();
        this.shopRepository = shopRepository;
        this.kakaoApiUrl = kakaoApiUrl;
        this.kakaoApiKey = kakaoApiKey;
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

    public Map<?,?> nowLocation(String x, String y) {

        // api 요청
        String url = kakaoApiUrl + "/v2/local/geo/coord2regioncode.json?";
        if (x != null && !x.isEmpty()) {
            url += "x=" + x + "&";
        }
        if (y != null && !y.isEmpty()) {
            url += "y=" + y + "&";
        }


        Map<?, ?> response =
                webClient
                        .get()
                        .uri(url)
                        .header("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                        .header("Authorization", "KakaoAK " + kakaoApiKey)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();


        Map<String, String> result = new HashMap<>();

        List<?> document = Objects.requireNonNull(response).get("documents") != null ? (List<?>) response.get("documents") : null;
        if (document != null) {
            Object o  = document.get(0);
            Map<String, String> map =  (Map<String, String>) o;
            String region_1depth_name = map.get("region_1depth_name");
            String region_2depth_name = map.get("region_2depth_name");
            String region_3depth_name = map.get("region_3depth_name");

            result.put("depth1", region_1depth_name);
            result.put("depth2", region_2depth_name);
            result.put("depth3", region_3depth_name);
        }


        // 결과 확인
        log.info(Objects.requireNonNull(response).toString());
        return result;

    }
}