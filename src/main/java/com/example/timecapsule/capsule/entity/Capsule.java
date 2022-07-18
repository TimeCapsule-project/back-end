package com.example.timecapsule.capsule.entity;

import com.example.timecapsule.config.BaseEntity;
import com.example.timecapsule.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
//TODO SET 사용 자제
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Capsule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long capsuleId;
    @Embedded
    private CapsuleInfo capsuleInfo;
    @Embedded
    private Recipient recipient;
    private String senderId;
    private Boolean isOpened;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id")
    public User user;

    @Builder
    public Capsule(CapsuleInfo capsuleInfo, Recipient recipient, User user,Boolean isOpened) {
        setCapsuleInfo(capsuleInfo);
        setRecipient(recipient);
        setOpened(isOpened);
        setUser(user);
        setSenderId(user.getUserId());
    }
    public void setSenderId(String userId){
        senderId=userId;
    }
    public void setOpened(Boolean opened) {
        this.isOpened = opened;
    }

    private void setCapsuleInfo(CapsuleInfo capsuleInfo) {
        if (capsuleInfo == null) throw new NullPointerException();
        this.capsuleInfo = capsuleInfo;
    }


    private void setRecipient(Recipient recipient) {
        if (recipient == null) throw new NullPointerException();
        this.recipient = recipient;
    }

    private void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getCapsules().remove(this);
        }
        this.user = user;
        user.getCapsules().add(this);
    }
}
