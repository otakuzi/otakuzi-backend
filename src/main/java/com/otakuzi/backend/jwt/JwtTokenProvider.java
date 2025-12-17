package com.otakuzi.backend.jwt;

import com.otakuzi.backend.dto.auth.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // 혹은 getBytes()
        this.key = Keys.hmacShaKeyFor(keyBytes); // HMAC SHA 알고리즘 사용
    }

    // 유저 정보를 받아서 AccessToken, RefreshToken 생성
    public TokenDto generateToken(String email, String role) {
        long now = (new Date()).getTime();

        // 1. Access Token 생성 (유효기간 30분)
        Date accessTokenExpiresIn = new Date(now + 1000 * 60 * 30);
        String accessToken = Jwts.builder()
                .setSubject(email) // 토큰 주인 (이메일)
                .claim("auth", role) // 권한 정보 (ROLE_USER 등)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 2. Refresh Token 생성 (유효기간 7일)
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 1000 * 60 * 60 * 24 * 7))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // (나중에 추가할 것: 토큰 검증, 토큰에서 유저 정보 꺼내기 등)
}