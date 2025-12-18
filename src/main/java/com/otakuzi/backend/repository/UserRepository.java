package com.otakuzi.backend.repository;

import com.otakuzi.backend.constant.UserType;
import com.otakuzi.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByProviderAndProviderId(String provider, String providerId);
    List<User> findAllByNickname(String nickname);
    List<User> findAllByUserType(UserType userType);
}