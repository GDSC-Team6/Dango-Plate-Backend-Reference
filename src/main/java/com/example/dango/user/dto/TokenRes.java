package com.example.dango.user.dto;


import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TokenRes {
    private String username;
    private String accessToken;
    private String refreshToken;
}
