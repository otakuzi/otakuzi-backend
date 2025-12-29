package com.otakuzi.backend.dto.admin;

import com.otakuzi.backend.constant.UserType;
import com.otakuzi.backend.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
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
