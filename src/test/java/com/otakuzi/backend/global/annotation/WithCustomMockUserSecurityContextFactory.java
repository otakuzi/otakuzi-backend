package com.otakuzi.backend.global.annotation;

import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.global.config.auth.PrincipalDetails;
import com.otakuzi.backend.global.constant.UserType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.test.util.ReflectionTestUtils;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = User.builder()
                .nickname("테스트유저")
                .email(annotation.email())
                .type(UserType.valueOf(annotation.role()))
                .build();

        ReflectionTestUtils.setField(user, "id", Long.parseLong(annotation.userId()));

        PrincipalDetails principalDetails = new PrincipalDetails(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                principalDetails,
                null,
                principalDetails.getAuthorities()
        );

        context.setAuthentication(auth);

        return context;
    }
}