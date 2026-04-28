package com.otakuzi.backend.domain.user.controller;

import com.otakuzi.backend.global.api.ApiResponse;
import com.otakuzi.backend.global.constant.UserType;
import com.otakuzi.backend.domain.user.dto.AdminUserResponse;
import com.otakuzi.backend.domain.user.dto.AdminUserUpdateRequest;
import com.otakuzi.backend.domain.user.service.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "관리자(Admin)", description = "관리자 페이지입니다.")
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    @Operation(summary = "사용자 조회", description = "사용자를 전체 조회합니다.")
    public ApiResponse<List<AdminUserResponse>> getAllUsers(
        @Parameter(description = "검색할 사용자 닉네임(부분 일치)")
        @RequestParam(required = false) String nickname,
        @Parameter(description = "검색할 권한 사용자")
        @RequestParam(required = false) UserType type
    ) {
        if (nickname != null) {
            return ApiResponse.success(adminUserService.adminGetUsersByNickname(nickname));
        }
        if (type != null) {
            return ApiResponse.success(adminUserService.adminGetUsersByUserType(type));
        }

        return ApiResponse.success(adminUserService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "사용자 조희", description = "특정 사용자를 조회합니다.")
    public ApiResponse<AdminUserResponse> getUserById(
        @Parameter(description = "검색할 사용자 아이디")
        @PathVariable Long id
    ) {
        return ApiResponse.success(adminUserService.getUserDetail(id));
    }

    @PutMapping({"/{id}"})
    @Operation(summary = "사용자 수정", description = "사용자 정보를 수정합니다.")
    public ApiResponse<Void> updateUser(
            @PathVariable Long id,
            @RequestBody AdminUserUpdateRequest userUpdateDto) {
        adminUserService.updateUserByAdmin(id, userUpdateDto);

        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "사용자 삭제", description = "사용자 정보를 삭제합니다.")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {

        adminUserService.adminDeleteUser(id);

        return ApiResponse.success();
    }
}
