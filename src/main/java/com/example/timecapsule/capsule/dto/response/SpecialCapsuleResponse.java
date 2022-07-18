package com.example.timecapsule.capsule.dto.response;

import com.example.timecapsule.capsule.entity.Capsule;
import com.example.timecapsule.capsule.repository.CapsuleRepository;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.repository.UserRepository;
import lombok.*;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SpecialCapsuleResponse {
    Long capsuleId;
    String content;
    String nickname;
    String recipient;
    LocalDateTime duration;
    Point2D.Double location;
    Byte capsuleType;
    Boolean opened;

    public static SpecialCapsuleResponse toCapsuleResponse(Capsule capsule) {
        return SpecialCapsuleResponse.builder()
                .capsuleId(capsule.getCapsuleId())
                .content(capsule.getCapsuleInfo().getCapsuleContent())
                .nickname(capsule.getCapsuleInfo().getNickname())
                .recipient(capsule.getRecipient().getRecipient())
                .duration(capsule.getCapsuleInfo().getDuration())
                .location(capsule.getCapsuleInfo().getLocation())
                .capsuleType(capsule.getCapsuleInfo().getCapsuleType())
                .opened(capsule.getIsOpened())
                .build();
    }

}
