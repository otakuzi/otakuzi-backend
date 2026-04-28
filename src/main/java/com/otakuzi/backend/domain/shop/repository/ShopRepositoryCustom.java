package com.otakuzi.backend.domain.shop.repository;

import com.otakuzi.backend.domain.shop.entity.Shop;

import java.util.List;

public interface ShopRepositoryCustom {
    List<Shop> searchByFilters(String name, List<String> categories);
}
