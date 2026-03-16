package com.otakuzi.backend.service.user;

import com.otakuzi.backend.dto.user.UserResponse;
import com.otakuzi.backend.entity.user.User;
import com.otakuzi.backend.mapper.user.UserMapper;
import com.otakuzi.backend.repository.user.UserRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));

        return userMapper.toResponse(user);
    }
    @Transactional
    public void updateNickname(Long id, String newNickname) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));

        if (newNickname.equals(user.getNickname())) {
            return;
        }

        badWordValidator.validate(newNickname);

        if (userRepository.existsByNicknameIgnoreCase(newNickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        user.updateNickname(newNickname);

        log.info("닉네임 변경 완료: ID={} / {} -> {}", id, user.getNickname(), newNickname);
    }
}
