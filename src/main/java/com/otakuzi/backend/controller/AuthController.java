package com.otakuzi.backend.controller;

import com.otakuzi.backend.dto.auth.TokenDto;
import com.otakuzi.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 프론트에서 인가 코드를 받아오는 API
    // GET /api/auth/kakao/callback?code=xxxxxx
    @GetMapping("/kakao/callback")
    public ResponseEntity<TokenDto> kakaoLogin(@RequestParam("code") String code) {

        TokenDto tokenDto = authService.kakaoLogin(code);

        return ResponseEntity.ok(tokenDto);
    }
}