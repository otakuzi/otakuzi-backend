package com.otakuzi.backend.service.user;

import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.repository.UserRepository;
import com.otakuzi.backend.util.BadWordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BadWordValidator badWordValidator;

    @Transactional
    public void updateNickname(Long id, String newNickname) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));

        if (newNickname.equals(user.getNickname())) {
            return;
        }

        badWordValidator.validate(newNickname);

        if (userRepository.existsByNickname(newNickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        user.updateNickname(newNickname);

        log.info("닉네임 변경 완료: ID={} / {} -> {}", id, user.getNickname(), newNickname);
    }
}
