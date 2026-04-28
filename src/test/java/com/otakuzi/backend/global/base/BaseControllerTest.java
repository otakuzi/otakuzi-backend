package com.otakuzi.backend.global.base;

import com.otakuzi.backend.global.jwt.JwtFilter;
import com.otakuzi.backend.global.jwt.JwtTokenProvider;
import com.otakuzi.backend.domain.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willAnswer;

public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockitoBean
    protected JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    protected UserRepository userRepository;

    @MockitoBean
    protected JwtFilter jwtFilter;

    @BeforeEach
    public void setupFilter() throws ServletException, IOException {
        willAnswer(invocation -> {
            HttpServletRequest req = invocation.getArgument(0);
            HttpServletResponse res = invocation.getArgument(1);
            FilterChain chain = invocation.getArgument(2);
            chain.doFilter(req, res);
            return null;
        }).given(jwtFilter).doFilter(any(), any(), any());
    }
}
