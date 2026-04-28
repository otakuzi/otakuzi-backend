package com.otakuzi.backend.domain.shop.controller;

import com.otakuzi.backend.domain.shop.dto.ShopCategoryResponse;
import com.otakuzi.backend.domain.shop.dto.ShopResponse;
import com.otakuzi.backend.global.api.ApiResponse;
import com.otakuzi.backend.domain.shop.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "매장(Shop)", description = "매장 관련 API입니다.")
@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {
    
    private final ShopService shopService;
    
    // 전체 조회
    @GetMapping
    @Operation(summary = "매장 전체 조회", description = "조건 없이 호출하면 전체 조회, 조건이 있으면 필터링(AND)하여 조회합니다.")
    public ApiResponse<List<ShopResponse>> getAllShops(
        @Parameter(description = "검색할 매장 이름 (부분 일치)")
        @RequestParam(required = false) String name,

        @Parameter(description = "검색할 매장 카테고리 (여러개 일치 검색 가능)")
        @RequestParam(required = false) List<String> categories
    ) {
        List<ShopResponse> shops = shopService.searchShops(name, categories);

        return ApiResponse.success(shops);
    }

    @GetMapping("/categories")
    @Operation(summary = "카테고리 조회", description = "모든 매장 카테고리를 조회합니다.")
    public ApiResponse<List<ShopCategoryResponse>> getCategories() {

        List<ShopCategoryResponse> categories = shopService.getAllCategories();

        return ApiResponse.success(categories);
    }
}