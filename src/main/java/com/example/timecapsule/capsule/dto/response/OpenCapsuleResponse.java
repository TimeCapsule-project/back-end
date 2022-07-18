package com.example.timecapsule.capsule.dto.response;

import com.example.timecapsule.capsule.entity.Capsule;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenCapsuleResponse {
    Long capsuleId;
    Boolean isOpened;
    String nickname;


    public static OpenCapsuleResponse toOpenCapsule(Capsule capsule){
        return OpenCapsuleResponse.builder()
                .capsuleId(capsule.getCapsuleId())
                .isOpened(capsule.getIsOpened())
                .nickname(capsule.getCapsuleInfo().getNickname())
                .build();
    }

}
