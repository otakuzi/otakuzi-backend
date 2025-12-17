package com.otakuzi.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${admin.id}")
    private String adminId;

    @Value("${admin.pw}")
    private String adminPw;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // ★ 1. HTTP Basic 로그인 활성화 (브라우저 팝업창 뜸)
                .httpBasic(Customizer.withDefaults())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // ★ 2. Swagger 주소: 이제 '로그인한 사람(authenticated)'만 볼 수 있음
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).authenticated()

                        // 관리자 전용 API (/admin/** 로 시작하는 모든 것) -> 관리자만 가능!
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 프론트엔드 API는 여전히 누구나 접근 가능
                        .requestMatchers(HttpMethod.GET, "/api/shops/**").permitAll()

                        // 나머지는 다 허용 (개발 편의상)
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    // ★ 3. 메모리에 임시 관리자 계정 만들기 (DB 없이 작동)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder() // withDefaultPasswordEncoder 대신 builder 사용
                .username(adminId)
                .password(passwordEncoder().encode(adminPw)) // 암호화 기계로 비밀번호 감싸기
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    // 4. 비밀번호 암호화 기계 등록 (BCrypt 방식)
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 가장 많이 쓰는 강력한 암호화 방식입니다.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "https://otakuim.com",
                "https://www.otakuim.com"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}