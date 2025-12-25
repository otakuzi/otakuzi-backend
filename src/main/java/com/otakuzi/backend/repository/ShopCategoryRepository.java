package com.otakuzi.backend.repository;

import com.otakuzi.backend.entity.ShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopCategoryRepository extends JpaRepository<ShopCategory, Long> {
    // 나중에 이름으로 찾을 일이 생길 수 있으니 미리 추가
    Optional<ShopCategory> findByName(String name);
}