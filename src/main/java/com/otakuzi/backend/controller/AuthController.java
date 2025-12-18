package com.otakuzi.backend.controller;

import com.otakuzi.backend.dto.auth.TokenDto;
import com.otakuzi.backend.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 프론트에서 인가 코드를 받아오는 API
    // GET /api/auth/kakao/callback?code=xxxxxx
    @GetMapping("/kakao/callback")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) {

        TokenDto tokenDto = authService.kakaoLogin(code);

        // 쿠키 생성 (HttpOnly 설정)
        ResponseCookie cookie = ResponseCookie.from("accessToken", tokenDto.getAccessToken())
                .httpOnly(true)       // 자바스크립트 접근 불가 (보안 핵심)
                .secure(false)        // 로컬(http) 환경에서는 false여야 함. 배포(https)때는 true로 변경!
                .path("/")            // 모든 경로에서 쿠키 사용
                .maxAge(60 * 60 * 24) // 1일 동안 유지
                .sameSite("Lax")      // 같은 도메인끼리 전송
                .build();

        // 응답 헤더에 쿠키 추가
        response.addHeader("Set-Cookie", cookie.toString());

        // 바디에는 프론트가 판단할 정보(닉네임 등)만 보냄 (토큰은 안 보냄!)
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "로그인 성공");

        return ResponseEntity.ok(responseBody);
    }
}