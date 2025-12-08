package com.otakuzi.backend.controller;

import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "상점(Shop)", description = "상점 관련 API입니다.") // 1. API 그룹 이름 설정
@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {
    
    private final ShopService shopService;
    
    @Operation(summary = "상점 전체 조회", description = "모든 상점 목록을 조회하거나, 이름으로 검색합니다.") // 2. API 설명
    // 전체 조회
    @GetMapping
    public ResponseEntity<List<Shop>> getAllShops(
        @Parameter(description = "검색할 상점 이름 (부분 일치)") // 3. 파라미터 설명
        @RequestParam(required = false) String name
    ) {
        if (name != null) {
            return ResponseEntity.ok(shopService.getShopByNameContaining(name));
        }
        return ResponseEntity.ok(shopService.getAllShops());
    }
}