package com.otakuzi.backend.service.shop;

import com.otakuzi.backend.dto.shop.*;
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
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopCategoryRepository shopCategoryRepository;
    private final ShopMapper shopMapper;
    private final ShopCategoryMapper shopCategoryMapper;

    public List<ShopResponse> searchShops(String name, List<String> categories) {

        if (categories != null && categories.isEmpty()) {
            categories = null;
        }

        List<Shop> shops = shopRepository.searchByFilters(name, categories);

        return shopMapper.toResponseList(shops);
    }

    @Cacheable(value = "categories")
    @Transactional(readOnly = true)
    public List<ShopCategoryResponse> getAllCategories() {
        List<ShopCategory> categories = shopCategoryRepository.findAll();

        return shopCategoryMapper.toResponseList(categories);
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