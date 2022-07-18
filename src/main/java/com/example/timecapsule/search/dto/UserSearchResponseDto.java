package com.example.timecapsule.search.dto;

import com.example.timecapsule.user.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserSearchResponseDto {
    private Long id;
    private String userNickname;

    public static UserSearchResponseDto toUserSearchResponseDto(User user){
        return UserSearchResponseDto.builder()
                .id(user.getId())
                .userNickname(user.getUserNickname())
                .build();
    }
}
