package com.otakuzi.backend.repository;

import com.otakuzi.backend.entity.Shop;

import java.util.List;

public interface ShopRepositoryCustom {
    List<Shop> searchByFilters(String name, List<String> categories);
}
