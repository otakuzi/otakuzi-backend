package com.otakuzi.backend.domain.auth.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @Column(name = "rt_key")
    private String key; // 유저의 ID (String 변환)

    @Column(name = "token", nullable = false, length = 1000)
    private String token;

    @Builder
    public RefreshToken(String key, String token) {
        this.key = key;
        this.token = token;
    }

    // 토큰 갱신 메서드
    public RefreshToken updateToken(String token) {
        this.token = token;
        return this;
    }
}