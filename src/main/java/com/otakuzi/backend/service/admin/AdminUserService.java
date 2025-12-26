package com.otakuzi.backend.service.admin;

import com.otakuzi.backend.constant.UserType;
import com.otakuzi.backend.dto.admin.AdminUserUpdateDto;
import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    public List<User> adminGetAllUsers() { return userRepository.findAll(); }

    public User adminGetUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));
    }

    public List<User> adminGetUsersByNickname(String nickname) {
        return userRepository.findAllByNickname(nickname);
    }

    public List<User> adminGetUsersByUserType(UserType type) {
        return userRepository.findAllBytype(type);
    }

    @Transactional
    public AdminUserUpdateDto adminUpdateUser(Long id, AdminUserUpdateDto dto) {

        // 원본 데이터 가져오기
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        // 원본 객체에 dto 값 덮어 씌우기
        user.updateAdminInfo(
            dto.getEmail(),
            dto.getProfileImage(),
            dto.getType(),
            dto.getIsDeleted()
        );

        return AdminUserUpdateDto.fromEntity(user);
    }

    @Transactional
    public void adminDeleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        userRepository.delete(user);
    }
}
