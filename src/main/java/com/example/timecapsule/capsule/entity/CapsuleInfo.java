package com.example.timecapsule.capsule.entity;

import com.example.timecapsule.capsule.dto.request.AnywhereCapsuleRequest;

import javax.persistence.Embeddable;
import java.awt.geom.Point2D;
import java.time.LocalDateTime;
@Embeddable
public class CapsuleInfo {
    private String capsuleContent;
    private LocalDateTime duration;
    private Point2D.Double location;
    private String nickname;
    //TODO 이넘 타입으로 변경 요망
    private Byte capsuleType;
    protected CapsuleInfo() {}

    public CapsuleInfo(String capsuleContent, LocalDateTime duration, String nickname, Byte capsuleType) {
        this.capsuleContent = capsuleContent;
        this.duration = duration;
        this.nickname = nickname;
        this.capsuleType = capsuleType;
    }
    public CapsuleInfo(String capsuleContent, LocalDateTime duration, Point2D.Double location, String nickname, Byte capsuleType) {
        this.capsuleContent = capsuleContent;
        this.duration = duration;
        this.location = location;
        this.nickname = nickname;
        this.capsuleType = capsuleType;
    }
    public void setCapsuleContent(String capsuleContent) {
        this.capsuleContent = capsuleContent;
    }

    public void setDuration(LocalDateTime duration) {
        this.duration = duration;
    }

    public void setLocation(Point2D.Double location) {
        this.location = location;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setCapsuleType(Byte capsuleType) {
        this.capsuleType = capsuleType;
    }

    public String getCapsuleContent() {
        return capsuleContent;
    }

    public LocalDateTime getDuration() {
        return duration;
    }

    public Point2D.Double getLocation() {
        return location;
    }

    public String getNickname() {
        return nickname;
    }

    public Byte getCapsuleType() {
        return capsuleType;
    }
}
