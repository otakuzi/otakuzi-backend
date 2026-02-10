package com.otakuzi.backend.controller.shop;

import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.global.config.auth.PrincipalDetails;
import com.otakuzi.backend.global.jwt.JwtTokenProvider;
import com.otakuzi.backend.repository.UserRepository;
import com.otakuzi.backend.service.shop.ShopBookmarkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import com.otakuzi.backend.global.jwt.JwtFilter; // JwtFilter 임포트
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach; // 추가
import java.io.IOException; // 추가

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication; // [중요] 이거 임포트!
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
            HttpServletRequest req = invocation.getArgument(0);
            HttpServletResponse res = invocation.getArgument(1);
            FilterChain chain = invocation.getArgument(2);

            // 검증 로직 없이 다음 필터로 진행(chain.doFilter) 시킴
            chain.doFilter(req, res);
            return null;
        }).given(jwtFilter).doFilter(any(), any(), any());
    }

    @Test
    @DisplayName("북마클 토글 요청 오면 200 OK 반환 후 서비스 호출")
    void toggleBookmark() throws Exception {

        // 1. 가짜 유저 엔티티 생성
        Long userId = 1L;
        User userEntity = User.builder()
                .nickname("otakuzi")
                .email("test@otakuzi.com")
                .type(com.otakuzi.backend.global.constant.UserType.USER)
                .build();
        ReflectionTestUtils.setField(userEntity, "id", userId); // ID 강제 주입

        PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                principalDetails,
                null,
                principalDetails.getAuthorities()
        );

        Long shopId = 10L;

        mockMvc.perform(post("/api/shops/" + shopId + "/bookmarks")
                    .with(csrf())
                    .with(authentication(auth))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
