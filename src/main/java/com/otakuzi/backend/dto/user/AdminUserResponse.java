package com.otakuzi.backend.dto.user;

import com.otakuzi.backend.global.constant.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AdminUserResponse {

    private Long id;
    private UserType type;
    private String email;
    private String nickname;
    private String profileImage;
    private String phoneNumber;
    private String provider;
    private LocalDateTime createdAt;
    private LocalDateTime loginAt;
    private Boolean isDeleted;

}
