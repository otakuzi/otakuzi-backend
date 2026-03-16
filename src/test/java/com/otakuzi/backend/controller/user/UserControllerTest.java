package com.otakuzi.backend.controller.user;

import com.otakuzi.backend.dto.user.UserResponse;
import com.otakuzi.backend.entity.user.User;
import com.otakuzi.backend.global.config.auth.PrincipalDetails;
import com.otakuzi.backend.global.jwt.JwtTokenProvider;
import com.otakuzi.backend.repository.user.UserRepository;
import com.otakuzi.backend.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @DisplayName("내 정보 조회 성공")
    void getUserInfo() throws Exception {

        Long userId = 1L;
        User userEntity = User.builder()
                .nickname("otakuzi")
                .email("test@otakuzi.com")
                .type(com.otakuzi.backend.global.constant.UserType.USER)
                .build();

        ReflectionTestUtils.setField(userEntity, "id", userId);

        PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

        UserResponse response = UserResponse.builder()
                .id(userId)
                .nickname("otakuzi")
                .email("test@otakuzi.com")
                .build();

        given(userService.getUserInfo(userId)).willReturn(response);

        mockMvc.perform(get("/api/users/me")
                            .with(user(principalDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value("otakuzi"))
                .andExpect(jsonPath("$.email").value("test@otakuzi.com"));
    }
}
