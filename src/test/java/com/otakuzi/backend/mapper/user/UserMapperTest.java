package com.otakuzi.backend.mapper.user;

import com.otakuzi.backend.dto.user.UserResponse;
import com.otakuzi.backend.entity.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    @DisplayName("User 엔티티가 Response DTO로 변환됨")
    void toResponseTest() {
        User user = User.builder()
                .email("test@otakuzi.com")
                .nickname("오타쿠지")
                .build();

        UserResponse response = mapper.toResponse(user);

        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo("test@otakuzi.com");
        assertThat(response.getNickname()).isEqualTo("오타쿠지");
    }
}
