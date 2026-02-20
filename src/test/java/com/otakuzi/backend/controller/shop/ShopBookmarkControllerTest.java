package com.otakuzi.backend.controller.shop;

import com.otakuzi.backend.global.annotation.WithCustomMockUser;
import com.otakuzi.backend.global.jwt.JwtTokenProvider;
import com.otakuzi.backend.repository.UserRepository;
import com.otakuzi.backend.service.shop.ShopBookmarkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.otakuzi.backend.global.jwt.JwtFilter; // JwtFilter 임포트
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach; // 추가
import java.io.IOException; // 추가

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.willAnswer; // 추가

@WebMvcTest(ShopBookmarkController.class)
class ShopBookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private ShopBookmarkService shopBookmarkService;

    @MockitoBean
    private JwtFilter jwtFilter;

    @BeforeEach
    public void setupFilter() throws ServletException, IOException {
        willAnswer(invocation -> {
            FilterChain chain = invocation.getArgument(2);
            chain.doFilter(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        }).given(jwtFilter).doFilter(any(), any(), any());
    }

    @Test
    @DisplayName("북마클 토글 요청 오면 200 OK 반환 후 서비스 호출 - 커스텀 어노테이션 사용")
    @WithCustomMockUser
    void toggleBookmark() throws Exception {

        Long shopId = 10L;

        mockMvc.perform(post("/api/shops/" + shopId + "/bookmarks")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
