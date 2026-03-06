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
        return DataSourceBuilder.create()
                .driverClassName("software.amazon.jdbc.Driver")
                .url(dbUrl)
                .username(username)
                .password(null)
                .build();
    }
}