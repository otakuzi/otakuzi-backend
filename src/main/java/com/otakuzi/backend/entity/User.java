package com.otakuzi.backend.entity;

import com.otakuzi.backend.global.constant.UserType;
import com.otakuzi.backend.entity.common.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_social_login", columnNames = {"provider", "provider_id"})
})

public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING) // DB에 문자열(ADMIN, USER...)로 저장
    @Column(name = "user_type", nullable = false)
    private UserType type;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname", nullable = false, length = 100)
    private String nickname;

    @Column(name = "profile_image", length = 500)
    private String profileImage;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "provider", nullable = false, length = 20)
    private String provider; // KAKAO, GOOGLE ...

    @Column(name = "provider_id", nullable = false, length = 200)
    private String providerId;

    @CreationTimestamp // INSERT 시 자동으로 시간 저장
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "login_at")
    private LocalDateTime loginAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Builder
    public User(UserType type, String email, String nickname, String profileImage,
                String phoneNumber, String provider, String providerId) {
        this.type = type != null ? type : UserType.USER; // 기본값 설정
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.phoneNumber = phoneNumber;
        this.provider = provider;
        this.providerId = providerId;
        this.isDeleted = false; // 기본값
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void updateLoginDate() {
        this.loginAt = LocalDateTime.now();
    }
}