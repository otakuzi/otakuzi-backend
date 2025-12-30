package com.otakuzi.backend.controller.user;

import com.otakuzi.backend.constant.UserType;
import com.otakuzi.backend.dto.user.AdminUserUpdateDto;
import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.service.admin.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> getAllUsers(
        @Parameter(description = "검색할 사용자 닉네임(부분 일치)")
        @RequestParam(required = false) String nickname,
        @Parameter(description = "검색할 권한 사용자")
        @RequestParam(required = false) UserType type
    ) {
        if (nickname != null) {
            return ResponseEntity.ok(adminUserService.adminGetUsersByNickname(nickname));
        }
        if (type != null) {
            return ResponseEntity.ok(adminUserService.adminGetUsersByUserType(type));
        }

        return ResponseEntity.ok(adminUserService.adminGetAllUsers());
    }

    @PutMapping({"/{id}"})
    @Operation(summary = "사용자 수정", description = "사용자 정보를 수정합니다.")
    public ResponseEntity<AdminUserUpdateDto> updateUser(
            @PathVariable Long id,
            @RequestBody AdminUserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(adminUserService.adminUpdateUser(id, userUpdateDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "사용자 삭제", description = "사용자 정보를 삭제합니다.")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        adminUserService.adminDeleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
