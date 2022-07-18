package com.example.timecapsule.user.dto.request;

import com.example.timecapsule.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserRequestDto {
    private String userId;
    private String userPw;
    private String userNickname;
    private String userEmail;
}
