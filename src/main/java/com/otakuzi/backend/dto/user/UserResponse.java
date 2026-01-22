package com.otakuzi.backend.dto.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String nickname;
    private String profileImage;
    private String phoneNumber;
    private LocalDateTime createdAt;
}
