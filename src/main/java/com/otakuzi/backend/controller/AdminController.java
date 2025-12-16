package com.otakuzi.backend.controller;

import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "관리자(Admin)", description = "관리자 페이지입니다.")
@RestController
@RequestMapping("/api/admin/shops")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    @Operation(summary = "매장 추가", description = "매장 정보를 새로 추가합니다.")
    public ResponseEntity<Shop> createShop(@RequestBody Shop shop) {
        return ResponseEntity.ok(adminService.adminCreateShop(shop));
    }

    @PutMapping({"/{id}"})
    @Operation(summary = "매장 수정", description = "해당 매장 정보를 수정합니다.")
    public ResponseEntity<Shop> updateShop(
            @PathVariable Long id,
            @RequestBody Shop shopDeatils) {
        return ResponseEntity.ok(adminService.adminUpdateShop(id, shopDeatils));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "매장 삭제", description = "해당 매장 정보를 삭제합니다.")
    public ResponseEntity<Shop> deleteShop(@PathVariable Long id) {
        adminService.adminDeleteShop(id);
        return ResponseEntity.noContent().build();
    }
}
