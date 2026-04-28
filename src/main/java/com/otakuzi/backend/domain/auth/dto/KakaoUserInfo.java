package com.otakuzi.backend.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoUserInfo implements OAuth2UserInfo {
    private Long id; // 카카오 고유 ID

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Data
    public static class KakaoAccount {
        private String email;
        private Profile profile;

        @Data
        public static class Profile {
            private String nickname;

            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }

    @Override
    public String getProviderId() {
        return String.valueOf(id);
    }

    @Override
    public String getProvider() {
        return "KAKAO";
    }

    @Override
    public String getEmail() {
        if (kakaoAccount == null) return null;
        return kakaoAccount.getEmail();
    }

    @Override
    public String getNickname() {
        if (kakaoAccount == null || kakaoAccount.getProfile() == null) return null;
        return kakaoAccount.getProfile().getNickname();
    }

    @Override
    public String getProfileImage() {
        if (kakaoAccount == null || kakaoAccount.getProfile() == null) return null;
        return kakaoAccount.getProfile().getProfileImageUrl();
    }
}
