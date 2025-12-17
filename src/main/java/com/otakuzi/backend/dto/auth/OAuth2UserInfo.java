package com.otakuzi.backend.dto.auth;

public interface OAuth2UserInfo {
    String getProviderId(); // 소셜 ID
    String getProvider();   // kakao, google, naver
    String getEmail();      // 이메일
    String getNickname();   // 닉네임
    String getProfileImage(); // 프로필 사진
}