package com.otakuzi.backend.service.user;

import com.otakuzi.backend.dto.user.UserResponse;
import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.global.util.BadWordValidator;
import com.otakuzi.backend.mapper.user.UserMapper;
import com.otakuzi.backend.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BadWordValidator badWordValidator;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("내 정보 조회 - 성공 시 DTO 반환")
    void getUserInfo() {

        Long userId = 1L;
        User user = User.builder().email("test@otakuzi.com").build();

        ReflectionTestUtils.setField(user, "id", userId);

        UserResponse expectedResponse = new UserResponse();

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(userMapper.toResponse(any(User.class))).willReturn(expectedResponse);

        UserResponse result = userService.getUserInfo(userId);

        assertThat(result).isEqualTo(expectedResponse);
        verify(userRepository).findById(userId);
    }

    @Test
    @DisplayName("닉네임 변경 성공 - 비속어 x, 중복 x")
    void updateNickname_Success() {

        Long userId = 1L;
        String newNickname = "클린한닉네임";
        User user = User.builder().nickname("옛날닉네임").build();

        ReflectionTestUtils.setField(user, "id", userId);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(userRepository.existsByNicknameIgnoreCase(newNickname)).willReturn(false);

        doNothing().when(badWordValidator).validate(newNickname);

        userService.updateNickname(userId, newNickname);

        assertThat(user.getNickname()).isEqualTo(newNickname);

        verify(badWordValidator).validate(newNickname);
        verify(userRepository).existsByNicknameIgnoreCase(newNickname);
    }

    @Test
    @DisplayName("닉네임 변경 실패 - 비속어 o")
    void updateNickname_Fail_BadWord() {

        Long userId = 1L;
        String badNickname = "나쁜말";

        User user = User.builder().nickname("옛날닉네임").build();
        ReflectionTestUtils.setField(user, "id", userId);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        willThrow(new IllegalArgumentException("비속어 금지"))
                .given(badWordValidator).validate(badNickname);

        assertThatThrownBy(() -> userService.updateNickname(userId, badNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비속어 금지");

        assertThat(user.getNickname()).isEqualTo("옛날닉네임");
    }

    @Test
    @DisplayName("닉네임 변경 실패 - 중복")
    void updateNickname_Fail_Duplicate() {

        Long userId = 1L;
        String newNickname = "중복된닉네임";
        User user = User.builder().nickname("옛날닉네임").build();
        ReflectionTestUtils.setField(user, "id", userId);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        doNothing().when(badWordValidator).validate(newNickname);
        given(userRepository.existsByNicknameIgnoreCase(newNickname)).willReturn(true);

        assertThatThrownBy(() -> userService.updateNickname(userId, newNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용 중인 닉네임입니다.");

        assertThat(user.getNickname()).isEqualTo("옛날닉네임");

        verify(badWordValidator).validate(newNickname);
        verify(userRepository).existsByNicknameIgnoreCase(newNickname);
    }
}
