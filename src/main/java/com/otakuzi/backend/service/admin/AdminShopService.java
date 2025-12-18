package com.otakuzi.backend.service.admin;

import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminShopService {

    private final ShopRepository shopRepository;

    public Shop adminCreateShop(Shop shop) {
        return shopRepository.save(shop);
    }

    public Shop adminUpdateShop(Long id, Shop shopDetails) {
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

    public void adminDeleteShop(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("매장 정보가 없습니다."));

        shopRepository.delete(shop);
    }
}
