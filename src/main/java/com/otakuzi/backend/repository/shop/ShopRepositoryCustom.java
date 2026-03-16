package com.otakuzi.backend.repository.shop;

import com.otakuzi.backend.entity.shop.Shop;

import java.util.List;

public interface ShopRepositoryCustom {
    List<Shop> searchByFilters(String name, List<String> categories);
}
