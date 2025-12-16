package com.otakuzi.backend.service;

import com.otakuzi.backend.entity.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final ShopService shopService;

    public Shop adminCreateShop(Shop shop) {
        return shopService.createShop(shop);
    }

    public Shop adminUpdateShop(Long id, Shop shopDetails) {
        return shopService.updateShop(id, shopDetails);
    }

    public void adminDeleteShop(Long id) {
        shopService.deleteShop(id);
    }
}
