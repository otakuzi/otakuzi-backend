package com.otakuzi.backend.domain.user.controller;

import com.otakuzi.backend.domain.user.dto.UserNicknameUpdateRequest;
import com.otakuzi.backend.domain.user.dto.UserResponse;
import com.otakuzi.backend.global.api.ApiResponse;
import com.otakuzi.backend.global.config.auth.PrincipalDetails;
import com.otakuzi.backend.global.exception.CustomException;
import com.otakuzi.backend.global.exception.ErrorCode;
import com.otakuzi.backend.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ApiResponse<UserResponse> getUserInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long id = principalDetails.getUserId();

        UserResponse response = userService.getUserInfo(id);

        return ApiResponse.success(response);
    }

    @PatchMapping("/nickname")
    @Operation(summary = "닉네임 변경", description = "로그인한 사용자의 닉네임을 변경합니다. 금지어/중복값 확인")
    public ApiResponse<Void> updateNickname(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody @Valid UserNicknameUpdateRequest dto) {

        if (principalDetails == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        Long id = principalDetails.getUserId();

        log.info("닉네임 변경 요청 UserID: {}, NewNickname: {}", id, dto.getNickname());

        userService.updateNickname(id, dto.getNickname());

        return ApiResponse.success();
    }
}
