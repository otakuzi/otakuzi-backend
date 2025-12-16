package com.otakuzi.backend.service;

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

    public Shop createShop(Shop shop) {
        return shopRepository.save(shop);
    }

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

    public Shop updateShop(Long id, Shop shopDetails) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("매장 정보가 없습니다."));

        shop.setPlaceName(shopDetails.getPlaceName());
        shop.setCategoryName(shopDetails.getCategoryName());
        shop.setPhone(shopDetails.getPhone());
        shop.setAddressName(shopDetails.getAddressName());
        shop.setRoadAddressName(shopDetails.getRoadAddressName());
        shop.setX(shopDetails.getX());
        shop.setY(shopDetails.getY());
        shop.setPlaceUrl(shopDetails.getPlaceUrl());

        return shopRepository.save(shop);
    }

    public void deleteShop(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("매장 정보가 없습니다."));

        shopRepository.delete(shop);
    }
}