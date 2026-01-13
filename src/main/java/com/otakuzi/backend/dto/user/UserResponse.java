package com.otakuzi.backend.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String nickname;
    private String profileImage;
    private String phoneNumber;
    private LocalDateTime createdAt;
}
