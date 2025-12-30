package com.otakuzi.backend.service.user;

import com.otakuzi.backend.constant.UserType;
import com.otakuzi.backend.dto.user.AdminUserResponse;
import com.otakuzi.backend.dto.user.AdminUserUpdateRequest;
import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.mapper.user.UserMapper;
import com.otakuzi.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<AdminUserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return userMapper.toAdminResponseList(users);
    }

    public AdminUserResponse getUserDetail(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));

        return userMapper.toAdminResponse(user);
    }

    public List<AdminUserResponse> adminGetUsersByNickname(String nickname) {
        List<User> users = userRepository.findAllByNickname(nickname);

        return userMapper.toAdminResponseList(users);
    }

    public List<AdminUserResponse> adminGetUsersByUserType(UserType type) {
        List<User> users = userRepository.findAllByType(type);

        return userMapper.toAdminResponseList(users);
    }

    @Transactional
    public void updateUserByAdmin(Long id, AdminUserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));

        userMapper.updateFromDto(request, user);
    }

    @Transactional
    public void adminDeleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));

        userRepository.delete(user);
    }

}
