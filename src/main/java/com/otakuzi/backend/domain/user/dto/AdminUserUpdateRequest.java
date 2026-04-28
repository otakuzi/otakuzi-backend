package com.otakuzi.backend.domain.user.dto;

import com.otakuzi.backend.global.constant.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminUserUpdateRequest {
    private String email;
    private String nickname;
    private String profileImage;
    private UserType type;
    private Boolean isDeleted;
}