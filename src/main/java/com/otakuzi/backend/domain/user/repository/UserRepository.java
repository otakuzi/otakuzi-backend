package com.otakuzi.backend.domain.user.repository;

import com.otakuzi.backend.global.constant.UserType;
import com.otakuzi.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByProviderAndProviderId(String provider, String providerId);
    List<User> findAllByNickname(String nickname);
    List<User> findAllByType(UserType type);
    boolean existsByNicknameIgnoreCase(String nickname);
}