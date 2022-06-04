package com.example.timecapsule.config;

import com.example.timecapsule.exception.NOTFOUNDEXCEPTION;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    private SecurityUtil() { }

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 메서드에서 저장
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new NOTFOUNDEXCEPTION();
        }

        return Long.parseLong(authentication.getName());
    }
}