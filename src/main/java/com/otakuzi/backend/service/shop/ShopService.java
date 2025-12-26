package com.otakuzi.backend.service.shop;

import com.otakuzi.backend.dto.admin.AdminShopCreateRequest;
import com.otakuzi.backend.dto.admin.AdminShopResponse;
import com.otakuzi.backend.dto.admin.AdminShopUpdateDto;
import com.otakuzi.backend.dto.shop.ShopResponse;
import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.ShopCategory;
import com.otakuzi.backend.repository.ShopCategoryRepository;
import com.otakuzi.backend.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {
    
    private final ShopRepository shopRepository;
    private final ShopCategoryRepository shopCategoryRepository;

    // ==========================================
    //  1. 조회 로직 (Read)
    // ==========================================

    /** 전체 상점 조회 (DTO로 변환해서 리턴) */
    public List<ShopResponse> getAllShops() {
        return shopRepository.findAll().stream()
                .map(ShopResponse::new)
                .collect(Collectors.toList());
    }

    /** 이름 포함 검색 */
    public List<ShopResponse> getShopsByNameContaining(String name) {
        return shopRepository.findByNameContaining(name).stream()
                .map(ShopResponse::new)
                .collect(Collectors.toList());
    }

    /** [관리자용] 전체 조회 */
    public List<AdminShopResponse> getAllShopsForAdmin() {
        return shopRepository.findAll().stream()
                .map(AdminShopResponse::new)
                .collect(Collectors.toList());
    }

    /** [관리자용] 상세 조회 (수정 화면용) */
    public AdminShopResponse getShopForAdmin(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("상점 없음"));
        return new AdminShopResponse(shop);
    }

    // ==========================================
    //  2. 관리자용 생성/수정/삭제 (CUD)
    // ==========================================

    /** [생성] 관리자용 상점 등록 */
    @Transactional
    public Long createShopByAdmin(AdminShopCreateRequest request) {
        Shop shop = Shop.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .addressName(request.getAddressName())
                .roadAddressName(request.getRoadAddressName())
                .x(request.getX())
                .y(request.getY())
                .placeUrl(request.getPlaceUrl())
                .build();

        connectCategories(shop, request.getCategoryIds());

        return shopRepository.save(shop).getId();
    }

    /** [수정] 관리자용 상점 수정 */
    @Transactional
    public void updateShopByAdmin(Long id, AdminShopUpdateDto dto) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상점 정보가 없습니다."));

        if (dto.getName() != null) shop.setName(dto.getName());
        if (dto.getPhone() != null) shop.setPhone(dto.getPhone());
        if (dto.getAddressName() != null) shop.setAddressName(dto.getAddressName());
        if (dto.getRoadAddressName() != null) shop.setRoadAddressName(dto.getRoadAddressName());
        if (dto.getX() != null) shop.setX(dto.getX());
        if (dto.getY() != null) shop.setY(dto.getY());
        if (dto.getPlaceUrl() != null) shop.setPlaceUrl(dto.getPlaceUrl());

        if (dto.getCategoryIds() != null) {
            shop.getShopCategoryMaps().clear();
            connectCategories(shop, dto.getCategoryIds());
        }
    }

    /** [삭제] 관리자용 상점 삭제 */
    @Transactional
    public void deleteShop(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상점 정보가 없습니다."));

        shopRepository.delete(shop);
    }

    // ==========================================
    //  3. 내부 편의 메서드 (중복 제거용)
    // ==========================================
    private void connectCategories(Shop shop, List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) return;

        for (Long id : categoryIds) {
            ShopCategory category = shopCategoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("없는 카테고리입니다."));
            shop.addCategory(category);
        }
    }
}