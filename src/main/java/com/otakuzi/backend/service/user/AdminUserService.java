package com.otakuzi.backend.service.user;

import com.otakuzi.backend.dto.admin.AdminUserResponse;
import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.mapper.UserMapper;
import com.otakuzi.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AdminUserResponse getUserDetail(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));

        return userMapper.toAdminResponse(user);
    }

    @Transactional
    public void updateUserByAdmin(Long id, String email, String profileImage, com.otakuzi.backend.constant.UserType type, Boolean isDeleted) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));

        user.updateAdminInfo(email, profileImage, type, isDeleted);
    }

}
