package com.otakuzi.backend.controller.shop;

import com.otakuzi.backend.dto.shop.ShopResponse;
import com.otakuzi.backend.service.shop.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "매장(Shop)", description = "매장 관련 API입니다.")
@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {
    
    private final ShopService shopService;
    
    @Operation(summary = "매장 전체 조회", description = "조건 없이 호출하면 전체 조회, 조건이 있으면 필터링(AND)하여 조회합니다.")
    // 전체 조회
    @GetMapping
    public ResponseEntity<List<ShopResponse>> getAllShops(
        @Parameter(description = "검색할 매장 이름 (부분 일치)")
        @RequestParam(required = false) String name,

        @Parameter(description = "검색할 매장 카테고리 (여러개 일치 검색 가능)")
        @RequestParam(required = false) List<String> categories
    ) {
        return ResponseEntity.ok(shopService.searchShops(name, categories));
    }
}