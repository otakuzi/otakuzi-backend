package com.otakuzi.backend.service.auth;

import com.otakuzi.backend.dto.auth.OAuth2UserInfo;
import com.otakuzi.backend.dto.auth.TokenDto;
import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.jwt.JwtTokenProvider;
import com.otakuzi.backend.repository.UserRepository;
import com.otakuzi.backend.constant.UserType;
import com.otakuzi.backend.dto.auth.KakaoTokenResponse;
import com.otakuzi.backend.dto.auth.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RestTemplate restTemplate = new RestTemplate(); // 외부 API 호출 도구

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    // 1. 로그인 전체 과정 지휘
    public TokenDto kakaoLogin(String code) {
        log.info("카카오 로그인 시도: code={}", code);

        // 1. 인가 코드로 카카오 액세스 토큰 받아오기
        String kakaoAccessToken = getKakaoAccessToken(code);

        // 2. 액세스 토큰으로 카카오 유저 정보 가져오기
        KakaoUserInfo kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken);

        // 3. DB 저장 or 갱신
        User user = registerOrUpdateUser(kakaoUserInfo);

        // 4. 우리 서비스 전용 JWT 토큰 발급
        TokenDto tokenDto = jwtTokenProvider.generateToken(user.getEmail(), user.getUserType().toString());

        // 일단은 테스트를 위해 유저 이름만 리턴해봅니다.
        return tokenDto;
    }

    // [내부 메서드 1] 카카오 토큰 요청
    private String getKakaoAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<KakaoTokenResponse> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                KakaoTokenResponse.class
        );

        return response.getBody().getAccessToken();
    }

    // [내부 메서드 2] 유저 정보 요청
    private KakaoUserInfo getKakaoUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserInfo> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                KakaoUserInfo.class
        );

        return response.getBody();
    }

    // [내부 메서드 3] 회원가입 또는 정보 업데이트
    private User registerOrUpdateUser(OAuth2UserInfo userInfo) {
        String provider = userInfo.getProvider();
        String providerId = userInfo.getProviderId();
        String email = userInfo.getEmail();
        String profileImage = userInfo.getProfileImage();

        // DB에서 providerId로 찾기
        User user = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElse(null);

        // 없으면 신규 가입
        if (user == null) {
            // 임시 닉네임
            String tempNickname = "임시_" + UUID.randomUUID().toString().substring(0, 8);

            user = User.builder()
                    .email(email != null ? email : "")
                    .nickname(tempNickname)
                    .provider(provider)
                    .providerId(providerId)
                    .profileImage(profileImage)
                    .userType(UserType.USER)
                    .build();
            userRepository.save(user);
        } else {
            user.updateLoginDate();
        }

        return user;
    }
}