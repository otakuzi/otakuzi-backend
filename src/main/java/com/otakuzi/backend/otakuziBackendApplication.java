package com.otakuzi.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.lang.reflect.Method;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class otakuziBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(otakuziBackendApplication.class, args);
	}

}
