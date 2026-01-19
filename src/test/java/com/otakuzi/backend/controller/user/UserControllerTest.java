package com.otakuzi.backend.controller.user;

import com.otakuzi.backend.dto.user.UserResponse;
import com.otakuzi.backend.global.jwt.JwtTokenProvider;
import com.otakuzi.backend.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerTest.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("내 정보 조회 성공")
    void getUserInfo() throws Exception {

        Long userId = 1L;
        UserResponse response = UserResponse.builder()
                .id(userId)
                .nickname("otakuzi")
                .email("test@otakuzi.com")
                .build();

        given(userService.getUserInfo(userId)).willReturn(response);

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value("otakuzi"))
                .andExpect(jsonPath("$.email").value("test@otakuzi.com"));
    }
}
