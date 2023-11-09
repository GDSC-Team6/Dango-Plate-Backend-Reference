package com.example.dango.user.dto;


import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TokenRes {
    private Long kakaoId;
    private String accessToken;
    private String refreshToken;
}
