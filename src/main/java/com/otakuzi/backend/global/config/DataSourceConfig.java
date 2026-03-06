package com.otakuzi.backend.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${RDS_URL_DEV:${RDS_URL_PROD:${RDS_URL:}}}")
    private String dbUrl;

    @Value("${DB_USERNAME}")
    private String username;

    @Bean
    @Primary
    public DataSource dataSource() {
        System.out.println("🚩🚩🚩 CURRENT DB URL: " + dbUrl);
        System.out.println("🚩🚩🚩 CURRENT DB USER: " + username);

        if (dbUrl == null || dbUrl.isEmpty()) {
            throw new RuntimeException("❌ DB URL이 주입되지 않았습니다. GitHub Secrets를 확인하세요.");
        }

        return DataSourceBuilder.create()
                .driverClassName("software.amazon.jdbc.Driver")
                .url(dbUrl)
                .username(username)
                .password("")
                .build();
    }
}