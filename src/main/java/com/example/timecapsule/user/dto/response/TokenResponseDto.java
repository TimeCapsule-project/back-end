package com.example.timecapsule.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TokenResponseDto {
    private Long userId;
    private String userEmail;
    private String userNickname;
    private String ACCESS_TOKEN;
    private String REFRESH_TOKEN;

    public TokenResponseDto(String ACCESS_TOKEN, String REFRESH_TOKEN){
        this.ACCESS_TOKEN = ACCESS_TOKEN;
        this.REFRESH_TOKEN = REFRESH_TOKEN;
    }
}
