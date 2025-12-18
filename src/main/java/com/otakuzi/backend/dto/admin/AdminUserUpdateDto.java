package com.otakuzi.backend.dto.admin;

import com.otakuzi.backend.constant.UserType;
import com.otakuzi.backend.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminUserUpdateDto {
    private String email;
    private String profileImage;
    private UserType userType;
    private Boolean isDeleted;

    // 역할: User(엔티티)를 주면, 내용을 베껴서 AdminUserUpdateDto(DTO)를 만들어 반환함
    public static AdminUserUpdateDto fromEntity(User user) {
        AdminUserUpdateDto dto = new AdminUserUpdateDto();

        // 하나씩 값 옮겨 적기 (매핑)
        dto.setEmail(user.getEmail());
        dto.setProfileImage(user.getProfileImage());
        dto.setUserType(user.getUserType());
        dto.setIsDeleted(user.getIsDeleted());

        return dto; // 완성된 DTO 반환
    }
}