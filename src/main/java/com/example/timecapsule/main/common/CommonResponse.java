package com.example.timecapsule.main.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponse {
    SUCCESS(0, "성공하였습니다."),

    FAIL(-1, "실패하였습니다."),

    INTERNALSERVERERROR(-500, "서버 에러"),
    NOTFOUND(-401,"찾기 오류"),
    NOTFOUNDUSER(-501,"찾는 유저가 없음"),
    DuplicateDATA(-502,"중복된 데이터"),
    NOT(-2012,"이건 오류"),
    PASSWORD(-504,"비밀번호오류"),
    ID(-503,"아이디오류"),
    UNAUTHORIZED(-402,"카카오 엑세스토큰 오류"),
    JWTEXPIRED(-2,"JWT 토큰 만료"),
    JWTNULL(-3, "JWT 토큰 없음"),
    JWTINVALID(-4, "JWT 토큰 유효하지 않음");

    private final int code;
    private final String msg;
}
