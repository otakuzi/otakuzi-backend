package com.otakuzi.backend.repository;

import com.otakuzi.backend.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    
    // 완료 여부로 조회
    List<Shop> findByPlaceNameContaining(String placeName);
    List<Shop> findAllByCategoryName(String categoryName);
}