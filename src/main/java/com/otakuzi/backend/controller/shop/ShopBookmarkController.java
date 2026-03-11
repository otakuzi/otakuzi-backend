package com.otakuzi.backend.controller.shop;

import com.otakuzi.backend.dto.shop.ShopBookmarkResponse;
import com.otakuzi.backend.global.config.auth.PrincipalDetails;
import com.otakuzi.backend.service.shop.ShopBookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name= "매장북마크(ShopBookmark)", description = "매장 즐겨찾기 관련 API입니다.")
@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopBookmarkController {

    private final ShopBookmarkService shopBookmarkService;

    @GetMapping("/bookmarks")
    @Operation(summary = "북마크한 매장 리스트", description = "내가 즐겨찾기한 매장 리스트입니다.")
    public ResponseEntity<List<ShopBookmarkResponse>> getMyBookmarks(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long userId = principalDetails.getUserId();

        List<ShopBookmarkResponse> responses = shopBookmarkService.getMyBookmarks(userId);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{shopId}/bookmarks")
    @Operation(summary = "매장 북마크 토글", description = "즐겨찾기를 없으면 추가, 있으면 삭제합니다.")
    public ResponseEntity<Void> toggleBookmark(
            @PathVariable(name = "shopId") Long shopId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long userId = principalDetails.getUserId();

        shopBookmarkService.toggleBookmark(userId, shopId);

        return ResponseEntity.ok().build();
    }
}
