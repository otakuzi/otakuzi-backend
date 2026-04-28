package com.otakuzi.backend.domain.shop.service;

import com.otakuzi.backend.domain.shop.dto.AdminShopCreateRequest;
import com.otakuzi.backend.domain.shop.dto.AdminShopResponse;
import com.otakuzi.backend.domain.shop.dto.AdminShopUpdateRequest;
import com.otakuzi.backend.domain.shop.dto.ShopCategoryResponse;
import com.otakuzi.backend.domain.shop.entity.Shop;
import com.otakuzi.backend.domain.shop.entity.ShopCategory;
import com.otakuzi.backend.domain.shop.mapper.ShopCategoryMapper;
import com.otakuzi.backend.domain.shop.mapper.AdminShopMapper;
import com.otakuzi.backend.domain.shop.repository.ShopCategoryRepository;
import com.otakuzi.backend.domain.shop.repository.ShopRepository;
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
    private final AdminShopMapper adminShopMapper;
    private final ShopCategoryMapper shopCategoryMapper;

    public List<AdminShopResponse> searchShopsForAdmin(String name, List<String> categories) {

        // 빈 리스트 null 처리 (동적 쿼리 오류 방지)
        if (categories != null && categories.isEmpty()) {
            categories = null;
        }

        List<Shop> shops = shopRepository.searchByFilters(name, categories);

        return adminShopMapper.toAdminResponseList(shops);
    }

    @Transactional
    public AdminShopResponse getShopForAdmin(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상점 없음"));

        return adminShopMapper.toAdminResponse(shop);
    }

    @Cacheable(value = "categories")
    public List<ShopCategoryResponse> getAllCategories() {
        List<ShopCategory> categories = shopCategoryRepository.findAll();

        return shopCategoryMapper.toResponseList(categories);
    }

    @Transactional
    public Long createShopByAdmin(AdminShopCreateRequest request) {
        Shop shop = adminShopMapper.toEntity(request);

        connectCategories(shop, request.getCategoryIds());

        return shopRepository.save(shop).getId();
    }

    @Transactional
    public void updateShopByAdmin(Long id, AdminShopUpdateRequest dto) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상점 정보가 없습니다."));

        adminShopMapper.updateFromDto(dto, shop);

        if (dto.getCategoryIds() != null) {
            shop.getShopCategoryMaps().clear();
            connectCategories(shop, dto.getCategoryIds());
        }
    }

    @Transactional
    public void deleteShop(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상점 정보가 없습니다."));

        shopRepository.delete(shop);
    }

    private void connectCategories(Shop shop, List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) return;

        List<ShopCategory> categories = shopCategoryRepository.findAllById(categoryIds);

        if (categories.size() != categoryIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 카테고리가 포함되어 있습니다.");
        }

        for (ShopCategory category : categories) {
            shop.addCategory(category);
        }
    }
}