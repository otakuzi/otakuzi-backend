package com.otakuzi.backend.global.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    // GitHub Secrets의 RDS_SECRET (시크릿 이름)
    @Value("${RDS_SECRET}")
    private String secretName;

    @Bean
    @Primary
    public DataSource dataSource() {
        // 1. AWS Secrets Manager 클라이언트 생성
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        // 2. 비밀번호 가져오기 요청
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;
        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw new RuntimeException("❌ AWS Secrets Manager에서 비밀번호를 가져오는데 실패했습니다: " + e.getMessage());
        }

        // 3. 받아온 JSON 파싱 (username, password 추출)
        String secretString = getSecretValueResponse.secretString();
        String user = "";
        String password = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(secretString);
            user = rootNode.path("username").asText();
            password = rootNode.path("password").asText();
            System.out.println("✅ DB 비밀번호 가져오기 성공! User: " + user);
        } catch (Exception e) {
            throw new RuntimeException("❌ 시크릿 JSON 파싱 실패", e);
        }

        // 4. DataSource 수동 생성 (이게 진짜 DB 연결 정보가 됨)
        return DataSourceBuilder.create()
                .driverClassName("software.amazon.jdbc.Driver")
                .url(dbUrl)
                .username(user)
                .password(password)
                .build();
    }
}