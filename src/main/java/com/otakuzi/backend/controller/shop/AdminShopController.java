package com.otakuzi.backend.controller.shop;

import com.otakuzi.backend.dto.shop.AdminShopCreateRequest;
import com.otakuzi.backend.dto.shop.AdminShopResponse;
import com.otakuzi.backend.dto.shop.AdminShopUpdateRequest;
import com.otakuzi.backend.dto.shop.ShopCategoryResponse;
import com.otakuzi.backend.service.shop.AdminShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "관리자(Admin)", description = "관리자 페이지입니다.")
@RestController
@RequestMapping("/api/admin/shops")
@RequiredArgsConstructor
public class AdminShopController {

    private final AdminShopService adminShopService;

    @GetMapping
    @Operation(summary = "매장 조회", description = "조건 없이 호출하면 전체 조회, 조건이 있으면 필터링하여 조회합니다.")
    public ResponseEntity<List<AdminShopResponse>> getAllShops(
            @Parameter(description = "검색할 매장 이름")
            @RequestParam(required = false) String name,

            @Parameter(description = "검색할 매장 카테고리")
            @RequestParam(required = false) List<String> categories
    ) {
        // 방금 만든 메서드 호출
        List<AdminShopResponse> shops = adminShopService.searchShopsForAdmin(name, categories);
        return ResponseEntity.ok(shops);
    }

    @GetMapping("/{id}")
    @Operation(summary = "매장 조회", description = "특정 매장 정보를 조회합니다.")
    public ResponseEntity<AdminShopResponse> getShop(@PathVariable Long id) {
        AdminShopResponse shop = adminShopService.getShopForAdmin(id);
        return ResponseEntity.ok(shop);
    }

    @PostMapping
    @Operation(summary = "매장 추가", description = "매장 정보를 새로 추가합니다.")
    public ResponseEntity<Long> createShop(@RequestBody AdminShopCreateRequest request) {
        Long id = adminShopService.createShopByAdmin(request);

        return ResponseEntity.created(URI.create("/api/admin/shops/" + id)).body(id);
    }

    @PutMapping({"/{id}"})
    @Operation(summary = "매장 수정", description = "해당 매장 정보를 수정합니다.")
    public ResponseEntity<Void> updateShop(
            @PathVariable Long id,
            @RequestBody AdminShopUpdateRequest dto) {

        adminShopService.updateShopByAdmin(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "매장 삭제", description = "해당 매장 정보를 삭제합니다.")
    public ResponseEntity<Void> deleteShop(@PathVariable Long id) {
        adminShopService.deleteShop(id);
        return ResponseEntity.ok().build(); // 200 OK
    }

    @GetMapping("/categories")
    @Operation(summary = "카테고리 조회", description = "모든 매장 카테고리를 조회합니다.")
    public ResponseEntity<List<ShopCategoryResponse>> getCategories() {
        return ResponseEntity.ok(adminShopService.getAllCategories());
    }
}
