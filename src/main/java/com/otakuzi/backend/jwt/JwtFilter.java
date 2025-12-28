package com.otakuzi.backend.jwt;

import com.otakuzi.backend.config.auth.PrincipalDetails;
import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 요청에서 "accessToken" 쿠키 찾기
        String token = null;

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }

        if (token == null && request.getCookies() != null) {
            token = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("accessToken")) // ★ 중요: 쿠키 이름 확인!
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }

        // 2. 토큰이 있고, 유효한지 검사 (Provider 사용)
        if (token != null && tokenProvider.validateToken(token)) {
            try {
                // 3. 토큰에서 userId 꺼내기
                Long userId = tokenProvider.getUserIdFromToken(token);

                // 4. DB에서 유저 정보 조회 (권한 확인을 위해)
                User user = userRepository.findById(userId).orElse(null);

                if (user != null) {
                    // 5. 시큐리티가 알아들을 수 있는 유저 객체(PrincipalDetails) 생성
                    PrincipalDetails principalDetails = new PrincipalDetails(user);

                    // 6. 인증 객체 만들기 (비밀번호는 null)
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                        principalDetails, null, principalDetails.getAuthorities());

                    // 7. ★ 핵심: 시큐리티 저장소에 "이 사람 로그인했음!" 도장 찍기
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                log.error("Security Context 설정 중 에러 발생", e);
            }
        }

        // 다음 필터로 넘기기 (필수)
        filterChain.doFilter(request, response);
    }
}