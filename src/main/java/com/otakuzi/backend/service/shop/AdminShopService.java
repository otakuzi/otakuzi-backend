package com.otakuzi.backend.service.shop;

import com.otakuzi.backend.dto.shop.AdminShopCreateRequest;
import com.otakuzi.backend.dto.shop.AdminShopResponse;
import com.otakuzi.backend.dto.shop.AdminShopUpdateRequest;
import com.otakuzi.backend.dto.shop.ShopCategoryResponse;
import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.ShopCategory;
import com.otakuzi.backend.mapper.shop.ShopCategoryMapper;
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
    private final ShopCategoryMapper shopCategoryMapper;

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
    public AdminShopResponse getShopForAdmin(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상점 없음"));

        return shopMapper.toAdminResponse(shop);
    }

    @Cacheable(value = "categories")
    public List<ShopCategoryResponse> getAllCategories() {
        List<ShopCategory> categories = shopCategoryRepository.findAll();

        return shopCategoryMapper.toResponseList(categories);
    }

    @Transactional
    public Long createShopByAdmin(AdminShopCreateRequest request) {
        Shop shop = shopMapper.toEntity(request);

        connectCategories(shop, request.getCategoryIds());

        return shopRepository.save(shop).getId();
    }

    @Transactional
    public void updateShopByAdmin(Long id, AdminShopUpdateRequest dto) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상점 정보가 없습니다."));

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