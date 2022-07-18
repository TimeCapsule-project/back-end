package com.example.timecapsule.user.dto.response;

import com.example.timecapsule.user.dto.request.UserRequestDto;
import com.example.timecapsule.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserResponseDto {
    private String userId;
    private String userNickname;

    public static User of(UserRequestDto userRequestDto, String password){
        return User.builder()
                .userId(userRequestDto.getUserId())
                .userPw(password)
                .userNickname(userRequestDto.getUserNickname())
                .userEmail(userRequestDto.getUserEmail())
                .userSearchEnabled(true)
                .build();
    }
    public static UserResponseDto toUserResponse (User user){
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .userNickname(user.getUserNickname())
                .build();
    }
}
