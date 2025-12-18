package com.otakuzi.backend.service.shop;

import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopService {
    
    private final ShopRepository shopRepository;

    // 전체 조회
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }
    
    // 이름 포함 검색
    public List<Shop> getShopByNameContaining(String name) {
        return shopRepository.findByPlaceNameContaining(name);
    }

    public List<Shop> getShopByCategoryName(String categoryName) {
        return shopRepository.findAllByCategoryName(categoryName);
    }
    }