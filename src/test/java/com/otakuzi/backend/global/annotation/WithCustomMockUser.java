package com.otakuzi.backend.global.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class) // 다음 단계에서 만들 클래스 연결
public @interface WithCustomMockUser {

    // 테스트마다 다르게 설정하고 싶은 값들을 여기에 적습니다.
    String userId() default "1";
    String email() default "test@otakuzi.com";
    String role() default "USER";
}
