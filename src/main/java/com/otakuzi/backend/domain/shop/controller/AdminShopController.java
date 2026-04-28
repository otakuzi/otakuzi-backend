package com.otakuzi.backend.domain.shop.controller;

import com.otakuzi.backend.domain.shop.dto.AdminShopCreateRequest;
import com.otakuzi.backend.domain.shop.dto.AdminShopResponse;
import com.otakuzi.backend.domain.shop.dto.AdminShopUpdateRequest;
import com.otakuzi.backend.domain.shop.dto.ShopCategoryResponse;
import com.otakuzi.backend.global.api.ApiResponse;
import com.otakuzi.backend.domain.shop.service.AdminShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "관리자(Admin)", description = "관리자 페이지입니다.")
@RestController
@RequestMapping("/api/admin/shops")
@RequiredArgsConstructor
public class AdminShopController {

    private final AdminShopService adminShopService;

    @GetMapping
    @Operation(summary = "매장 조회", description = "조건 없이 호출하면 전체 조회, 조건이 있으면 필터링하여 조회합니다.")
    public ApiResponse<List<AdminShopResponse>> getAllShops(
            @Parameter(description = "검색할 매장 이름")
            @RequestParam(required = false) String name,

            @Parameter(description = "검색할 매장 카테고리")
            @RequestParam(required = false) List<String> categories
    ) {
        // 방금 만든 메서드 호출
        List<AdminShopResponse> shops = adminShopService.searchShopsForAdmin(name, categories);
        return ApiResponse.success(shops);
    }

    @GetMapping("/{id}")
    @Operation(summary = "매장 조회", description = "특정 매장 정보를 조회합니다.")
    public ApiResponse<AdminShopResponse> getShop(@PathVariable Long id) {
        AdminShopResponse shop = adminShopService.getShopForAdmin(id);
        return ApiResponse.success(shop);
    }

    @PostMapping
    @Operation(summary = "매장 추가", description = "매장 정보를 새로 추가합니다.")
    public ApiResponse<Long> createShop(@RequestBody AdminShopCreateRequest request) {
        Long id = adminShopService.createShopByAdmin(request);

        return ApiResponse.success(id);
    }

    @PutMapping({"/{id}"})
    @Operation(summary = "매장 수정", description = "해당 매장 정보를 수정합니다.")
    public ApiResponse<Void> updateShop(
            @PathVariable Long id,
            @RequestBody AdminShopUpdateRequest dto) {

        adminShopService.updateShopByAdmin(id, dto);

        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "매장 삭제", description = "해당 매장 정보를 삭제합니다.")
    public ApiResponse<Void> deleteShop(@PathVariable Long id) {
        adminShopService.deleteShop(id);
        return ApiResponse.success(); // 200 OK
    }

    @GetMapping("/categories")
    @Operation(summary = "카테고리 조회", description = "모든 매장 카테고리를 조회합니다.")
    public ApiResponse<List<ShopCategoryResponse>> getCategories() {

        List<ShopCategoryResponse> categories = adminShopService.getAllCategories();

        return ApiResponse.success(categories);
    }
}
