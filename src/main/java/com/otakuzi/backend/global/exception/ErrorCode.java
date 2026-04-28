package com.otakuzi.backend.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // ===== 공통 =====
    INVALID_REQUEST(400, "잘못된 요청입니다."),
    UNAUTHORIZED(401, "로그인이 필요합니다."),
    FORBIDDEN(403, "권한이 없습니다."),
    NOT_FOUND(404, "페이지를 찾을 수 없습니다."),
    CONFLICT(409, "이미 존재하는 데이터입니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류입니다."),

    // ===== USER =====
    USER_001(404, "사용자 정보를 찾을 수 없습니다."),
    USER_002(409, "이미 사용 중인 닉네임입니다."),
    USER_003(400, "사용할 수 없는 닉네임입니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
