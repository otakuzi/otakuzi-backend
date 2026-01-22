package com.otakuzi.backend.controller.user;

import com.otakuzi.backend.dto.user.UserNicknameUpdateRequest;
import com.otakuzi.backend.dto.user.UserResponse;
import com.otakuzi.backend.global.config.auth.PrincipalDetails;
import com.otakuzi.backend.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자(User)", description = "사용자 페이지입니다.")
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "내 정보 확인", description = "내 정보를 불러와서 확인합니다.")
    public UserResponse getUserInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long id = principalDetails.getUserId();

        return userService.getUserInfo(id);
    }

    @PatchMapping("/nickname")
    @Operation(summary = "닉네임 변경", description = "로그인한 사용자의 닉네임을 변경합니다. 금지어/중복값 확인")
    public ResponseEntity<String> updateNickname(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody @Valid UserNicknameUpdateRequest dto) {

        if (principalDetails == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        Long id = principalDetails.getUserId();

        log.info("닉네임 변경 요청 UserID: {}, NewNickname: {}", id, dto.getNickname());

        userService.updateNickname(id, dto.getNickname());

        return ResponseEntity.ok("닉네임이 변경되었습니다.");
    }
}
