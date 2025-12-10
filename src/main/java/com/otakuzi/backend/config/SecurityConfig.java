package com.otakuzi.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. CSRF 보안 비활성화 (API 서버는 보통 session을 안써서 필요없음)
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. CORS 설정 적용 (아래 정의한 corsConfigurationSource를 사용)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 3. 주소별 권한 설정 (이게 제일 중요! ⭐)
            .authorizeHttpRequests(auth -> auth
                // Swagger 관련 주소는 누구나 접속 가능하게 허용
                .requestMatchers(
                    "/v3/api-docs/**", 
                    "/swagger-ui/**", 
                    "/swagger-ui.html"
                ).permitAll()
                
                // 프론트엔드가 사용할 API 주소 허용
                .requestMatchers("/api/shops/**").permitAll()
                
                // 그 외 모든 요청은 인증(로그인) 필요 (나중에 로그인 구현할 때 사용)
                // 개발 중이라 다 열고 싶으면 .anyRequest().authenticated() 대신 .anyRequest().permitAll() 로 바꾸세요.
                .anyRequest().permitAll() 
            );

        return http.build();
    }

    // CORS 설정 (프론트엔드에서 요청 보낼 때 허용해주는 설정)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 허용할 프론트엔드 도메인들
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",          // 로컬 개발용
            "https://otakuim.com",            // 운영 서버
            "https://www.otakuim.com"         // www 붙은 주소
        ));
        
        // 허용할 HTTP 메서드
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // 모든 헤더 허용
        configuration.setAllowedHeaders(List.of("*"));
        
        // 자격 증명(쿠키 등) 허용
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}