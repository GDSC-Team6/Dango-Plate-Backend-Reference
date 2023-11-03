package com.example.dango;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DangoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DangoApplication.class, args);
        //main 브랜치로 변경
    }
    /* Cors 설정 */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", "http://35.216.0.111:3000/") // 허용할 주소들
                        .allowedMethods("*") //모든 post, get, put, delete, patch 요청을 허용
                        .allowedHeaders("*") //모든 header에 응답을 허용
                        .allowCredentials(true); // 클라이언트가 자격 증명을 사용하여 요청을 할 수 있도록 허용
            }
        };
    }
}
