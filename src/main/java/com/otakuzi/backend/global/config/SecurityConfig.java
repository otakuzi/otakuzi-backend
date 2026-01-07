package com.otakuzi.backend.global.config;

import com.otakuzi.backend.global.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // ★ [중요 1] Preflight(OPTIONS) 요청은 무조건 허용 (JwtFilter 닿기 전에)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(org.springframework.web.cors.CorsUtils::isPreFlightRequest).permitAll()

                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 관리자
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // ★ [중요 2] shops 경로 명확하게 지정 (뒤에 슬래시 없는 것도 포함)
                        .requestMatchers(HttpMethod.GET, "/api/shops", "/api/shops/**").permitAll()

                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // ======================================================
        // 1. 관리자 전용 설정 (Localhost & Dev만 허용)
        // ======================================================
        CorsConfiguration adminConfig = new CorsConfiguration();
        adminConfig.setAllowedOriginPatterns(List.of(
                "http://localhost:3000",       // 로컬 프론트
                "https://dev.otakuim.com"      // 개발 서버 프론트
        ));
        adminConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        adminConfig.setAllowedHeaders(Arrays.asList("*"));
        adminConfig.setAllowCredentials(true);
        adminConfig.setMaxAge(3600L);

        // ★ 핵심: "/api/admin/**" 경로는 adminConfig를 따르도록 등록
        source.registerCorsConfiguration("/api/admin/**", adminConfig);


        // ======================================================
        // 2. 일반 공통 설정 (Prod 포함 모든 곳 허용)
        // ======================================================
        CorsConfiguration publicConfig = new CorsConfiguration();
        publicConfig.setAllowedOriginPatterns(List.of(
                "http://localhost:3000",
                "https://otakuim.com",          // 운영 서버 허용
                "https://www.otakuim.com",
                "https://dev.otakuim.com"
        ));
        publicConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        publicConfig.setAllowedHeaders(Arrays.asList("*"));
        publicConfig.setAllowCredentials(true);
        publicConfig.setMaxAge(3600L);

        // 나머지 모든 경로는 publicConfig를 따름
        source.registerCorsConfiguration("/**", publicConfig);

        return source;
    }
}