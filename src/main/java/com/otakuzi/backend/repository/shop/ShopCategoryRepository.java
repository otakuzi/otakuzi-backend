package com.otakuzi.backend.repository.shop;

import com.otakuzi.backend.entity.shop.ShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopCategoryRepository extends JpaRepository<ShopCategory, Long> {
    Optional<ShopCategory> findByName(String name);
}