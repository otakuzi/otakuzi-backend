package com.otakuzi.backend.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration

public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // jwt 설정
        String jwtSchemeName = "jwtAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server"),
                        new Server().url("https://dev-api.otakuim.com").description("Dev Server")
                ))
                .info(new Info()
                        .title("Otakuzi API 명세서")
                        .description("오타쿠지 프로젝트 API 문서입니다.")
                        .version("1.0.0"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    // ★ 그룹 1: 일반 사용자용 (기본으로 보여줄 것)
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("1. 일반 사용자용") // 화면에 표시될 그룹 이름
                .pathsToMatch("/api/shops/**", "/api/auth/**", "/api/users/**") // 이 경로만 포함
                .pathsToExclude("/api/admin/**") // 혹시 모르니 admin은 확실히 제외
                .build();
    }

    // ★ 그룹 2: 관리자용 (선택해야 보임)
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("2. 관리자 전용")
                .pathsToMatch("/api/admin/**") // admin 경로만 포함
                .build();
    }
}