package com.otakuzi.backend.controller;

import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {
    
    private final ShopService shopService;
    
    // 전체 조회
    @GetMapping
    public ResponseEntity<List<Shop>> getAllShops(
            @RequestParam(required = false) String name
    ) {
        if (name != null) {
            return ResponseEntity.ok(shopService.getShopByNameContaining(name));
        }
        return ResponseEntity.ok(shopService.getAllShops());
    }
}