package com.otakuzi.backend.global.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otakuzi.backend.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final LocalDateTime timestamp;
    private final int status;
    private final String code;
    private final String message;
    private final T data;

    private ApiResponse(int status, String code, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "SUCCESS",
                "요청에 성공했습니다",
                data
        );
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "SUCCESS",
                "요청에 성공했습니다",
                null
        );
    }

    public static ApiResponse<Void> error(ErrorCode errorCode) {
        return new ApiResponse<>(
                errorCode.getStatus(),
                errorCode.name(),
                errorCode.getMessage(),
                null
        );
    }
}
