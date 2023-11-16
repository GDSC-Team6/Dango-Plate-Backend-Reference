package com.example.dango.service;

import com.example.dango.kakaoMap.service.KakaoWebClientService;
import com.example.dango.shop.entity.Shop;
import com.example.dango.shop.repository.ShopRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KakaoWebClientServiceTest {
    @Mock
    private ShopRepository shopRepository;

    @Test
    public void getTest() {
        // given
        String query = "강남"; String x = "10"; String y = "10"; String radius = "100";
        KakaoWebClientService kakaoWebClientService = new KakaoWebClientService(shopRepository, "https://dapi.kakao.com", "7e74ddba8b7f254e7d0828a4a0dd1d2f");
        given(shopRepository.findByShopUid(Mockito.anyLong())).willReturn(Optional.empty());
        given(shopRepository.save(Mockito.any(Shop.class))).willReturn(null);
        // when
        Map<?, ?> ret = kakaoWebClientService.get(query, x, y, radius);
        // then
        assertNotNull(ret);
        verify(shopRepository, atLeastOnce()).findByShopUid(Mockito.anyLong());
        verify(shopRepository, atLeastOnce()).save(Mockito.any(Shop.class));
    }
}
