package com.otakuzi.backend.global.config.auth;

import com.otakuzi.backend.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private final User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // ★ 중요: 유저의 권한을 리턴하는 곳
    // UserType(ADMIN, USER)을 시큐리티가 아는 "ROLE_ADMIN", "ROLE_USER"로 바꿔줌
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // Enum 이름 앞에 "ROLE_"을 붙이는 것이 관례입니다.
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getType().name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // 소셜 로그인이라 비밀번호가 없음
    }

    @Override
    public String getUsername() {
        // 시큐리티가 식별할 ID (우리는 PK인 userId를 사용)
        return String.valueOf(user.getId());
    }

    // 우리 유저 객체를 꺼내쓸 때 필요함
    public User getUser() {
        return user;
    }

    // --- 아래는 계정 상태 확인용 (다 true로 설정) ---

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 탈퇴한 유저(isDeleted = true)라면 로그인 못하게 막음
        return !user.getIsDeleted();
    }

    public Long getUserId() {
        return user.getId();
    }
}