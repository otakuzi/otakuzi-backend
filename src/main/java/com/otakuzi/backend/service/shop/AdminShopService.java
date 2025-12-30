package com.otakuzi.backend.service.shop;

import com.otakuzi.backend.dto.shop.AdminShopResponse;
import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.mapper.shop.ShopMapper;
import com.otakuzi.backend.repository.ShopCategoryRepository;
import com.otakuzi.backend.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminShopService {

    private final ShopRepository shopRepository;
    private final ShopCategoryRepository shopCategoryRepository;
    private final ShopMapper shopMapper;

    public List<AdminShopResponse> getAllShops() {
        List<Shop> shops = shopRepository.findAll();

        return shopMapper.toAdminResponseList(shops);
    }

    public List<AdminShopResponse> searchShopsForAdmin(String name, List<String> categories) {

        // 빈 리스트 null 처리 (동적 쿼리 오류 방지)
        if (categories != null && categories.isEmpty()) {
            categories = null;
        }

        List<Shop> shops = shopRepository.searchByFilters(name, categories);

        return shopMapper.toAdminResponseList(shops);
    }

    @Transactional
    public AdminShopResponse getShopForAdmin(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("상점 없음"));
        return new AdminShopResponse(shop);
    }

    @Cacheable(value = "categories")
    @Transactional(readOnly = true)
    public List<AdminShopResponse.CategoryDto> getAllCategories() {
        return shopCategoryRepository.findAll().stream()
                .map(AdminShopResponse.CategoryDto::new)
                .collect(Collectors.toList());
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
    public void updateShopByAdmin(Long id, AdminShopUpdateRequest dto) {
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