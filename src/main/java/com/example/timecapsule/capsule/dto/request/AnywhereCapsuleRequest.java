package com.example.timecapsule.capsule.dto.request;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class AnywhereCapsuleRequest {
    String content;
    String nickname;
    Long recipient;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime duration;
    Byte capsuleType;

//    public Capsule toCapsule(Account account, LocalDateTime currentDate) {
//        Capsule capsule=new Capsule();
//        capsule.addAccount(account);
//        capsule.setCapsuleTitle(title);
//        capsule.setCapsuleContent(content);
//        capsule.setNickname(nickname);
//        capsule.setRecipient(recipient);
//        capsule.setDuration(currentDate.plusDays(duration));
//        capsule.setIsOpened(false);
//        capsule.setLocation(capsule.setLocationFunc(latitude,longitude));
//        capsule.setSenderId(account.getKakaoId());
//        return capsule;
//    }

//    public Capsule toCapsule(final User user, final LocalDateTime currentDate) {
//        Capsule capsules=new Capsule();
//        capsules.addUser(user);
//        capsules.setCapsuleTitle(title);
//        capsules.setCapsuleContent(content);
//        capsules.setNickname(nickname);
//        capsules.setRecipient(recipient);
//        capsules.setDuration(currentDate.plusDays(duration));
//        capsules.setIsOpened(false);
//        capsules.setLocation(capsules.setLocationFunc(latitude,longitude));
//        capsules.setSenderId(user.getUserId());
//        return capsules;
//    }
}
