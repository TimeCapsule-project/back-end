package com.example.timecapsule.capsule.dto.response;

import com.example.timecapsule.capsule.entity.Capsule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnywhereCapsuleResponse {
    Long capsuleId;
    String content;
    String nickname;
    String recipient;
    LocalDateTime duration;
    Point2D.Double location;
    Byte capsuleType;
    Boolean opened;
    public static AnywhereCapsuleResponse toCapsuleResponse(Capsule capsule) {
        return AnywhereCapsuleResponse.builder()
                .capsuleId(capsule.getCapsuleId())
                .content(capsule.getCapsuleInfo().getCapsuleContent())
                .nickname(capsule.getCapsuleInfo().getNickname())
                .recipient(capsule.getRecipient().getRecipient())
                .duration(capsule.getCapsuleInfo().getDuration())
                .capsuleType(capsule.getCapsuleInfo().getCapsuleType())
                .opened(capsule.getIsOpened())
                .build();
    }

}
