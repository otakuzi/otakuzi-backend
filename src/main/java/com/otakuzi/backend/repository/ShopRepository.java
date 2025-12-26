package com.otakuzi.backend.repository;

import com.otakuzi.backend.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    /**
     * 동적 쿼리 (Dynamic Query)
     * :name이 null이면 이름 조건 무시
     * :categories가 null이면 카테고리 조건 무시
     * 둘 다 있으면 AND 조건으로 검색
     */
    @Query("SELECT DISTINCT s FROM Shop s " +
            "LEFT JOIN s.shopCategoryMaps m " +
            "LEFT JOIN m.category c " +
            "WHERE " +
            "   (:name IS NULL OR s.name LIKE %:name%) " +
            "   AND " +
            "   (:categories IS NULL OR c.name IN :categories)")
    List<Shop> searchByFilters(@Param("name") String name, @Param("categories") List<String> categories);
}