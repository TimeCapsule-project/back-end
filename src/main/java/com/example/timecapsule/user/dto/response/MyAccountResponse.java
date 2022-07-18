package com.example.timecapsule.user.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MyAccountResponse {
    Long accountId;
    String accountEmail;
    String profileNickname;
    String accessToken;
    Long kakaoId;
    LocalDateTime createdAt;

}
