package com.otakuzi.backend.domain.auth.controller;

import com.otakuzi.backend.domain.auth.dto.TokenDto;
import com.otakuzi.backend.global.api.ApiResponse;
import com.otakuzi.backend.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${spring.profiles.active:local}") // 현재 프로필(local, prod 등) 가져오기
    private String activeProfile;

    // 프론트에서 인가 코드를 받아오는 API
    // GET /api/auth/kakao/callback?code=xxxxxx
    @GetMapping("/kakao/callback")
    public ApiResponse<?> kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) {

        TokenDto tokenDto = authService.kakaoLogin(code);

        boolean isProd = "prod".equals(activeProfile); // 배포 환경인지 체크

        // 쿠키 생성 (HttpOnly 설정)
        ResponseCookie cookie = ResponseCookie.from("accessToken", tokenDto.getAccessToken())
                .httpOnly(true)
                .secure(isProd)          // ★ 배포면 true, 로컬이면 false
                .path("/")
                .maxAge(60 * 60 * 24)
                .sameSite(isProd ? "None" : "Lax") // ★ 배포면 None(도메인 달라도 전송), 로컬이면 Lax
                .build();

        // 응답 헤더에 쿠키 추가
        response.addHeader("Set-Cookie", cookie.toString());

        // 바디에는 프론트가 판단할 정보(닉네임 등)만 보냄 (토큰은 안 보냄!)
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "로그인 성공");

        return ApiResponse.success(responseBody);
    }
}