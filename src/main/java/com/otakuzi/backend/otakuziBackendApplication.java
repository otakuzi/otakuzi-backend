package com.otakuzi.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.lang.reflect.Method;

@EnableJpaAuditing
@SpringBootApplication
public class otakuziBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(otakuziBackendApplication.class, args);
	}

}
