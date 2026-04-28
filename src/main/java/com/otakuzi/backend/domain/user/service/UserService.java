package com.otakuzi.backend.domain.user.service;

import com.otakuzi.backend.domain.user.dto.UserResponse;
import com.otakuzi.backend.domain.user.entity.User;
import com.otakuzi.backend.domain.user.mapper.UserMapper;
import com.otakuzi.backend.domain.user.repository.UserRepository;
import com.otakuzi.backend.global.exception.CustomException;
import com.otakuzi.backend.global.exception.ErrorCode;
import com.otakuzi.backend.global.util.BadWordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BadWordValidator badWordValidator;

    @Transactional(readOnly = true)
    public UserResponse getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_001));

        return userMapper.toResponse(user);
    }
    @Transactional
    public void updateNickname(Long id, String newNickname) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_001));

        if (newNickname.equals(user.getNickname())) {
            return;
        }

        badWordValidator.validate(newNickname);

        if (userRepository.existsByNicknameIgnoreCase(newNickname)) {
            throw new CustomException(ErrorCode.USER_002);
        }

        String oldNickname = user.getNickname();

        user.updateNickname(newNickname);

        log.info("닉네임 변경 완료: ID={} / {} -> {}", id, oldNickname, newNickname);
    }
}
