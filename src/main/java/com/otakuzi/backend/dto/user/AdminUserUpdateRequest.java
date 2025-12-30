package com.otakuzi.backend.dto.user;

import com.otakuzi.backend.constant.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminUserUpdateRequest {
    private String email;
    private String profileImage;
    private UserType type;
    private Boolean isDeleted;
}