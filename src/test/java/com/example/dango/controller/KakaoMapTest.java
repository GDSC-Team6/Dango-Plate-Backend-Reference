package com.example.dango.controller;

import com.example.dango.kakaoMap.controller.KakaoMapController;
import com.example.dango.kakaoMap.service.KakaoWebClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(value = KakaoMapController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { WebSecurityConfigurerAdapter.class})
})
public class KakaoMapTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private KakaoWebClientService kakaoWebClientService;

    @Test
    @WithMockUser
    @DisplayName("카카오 맵 검색 기본 테스트")
    public void searchTest() throws Exception {
        // given
        String query = "맛집";
        String x = "127.0";
        String y = "37.0";
        String radius = "1000";
        given(kakaoWebClientService.get(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).willReturn(new HashMap<>());
        // when
        ResultActions resultActions = mockMvc.perform(get("/map/search")
                .param("query", query)
                .param("x", x)
                .param("y", y)
                .param("radius", radius));
        // then
        resultActions
                .andExpect(status().isOk());
    }

}
