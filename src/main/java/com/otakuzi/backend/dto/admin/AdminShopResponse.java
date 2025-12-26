package com.otakuzi.backend.dto.admin;

import com.otakuzi.backend.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class AdminShopResponse {
    private Long shopId;
    private String name;
    private String phone;
    private String addressName;
    private String roadAddressName;
    private String x;
    private String y;
    private String placeUrl;

    private List<String> shopCategories;

    public AdminShopResponse(Shop shop) {
        this.shopId = shop.getId();
        this.name = shop.getName();
        this.phone = shop.getPhone();
        this.addressName = shop.getAddressName();
        this.roadAddressName = shop.getRoadAddressName();
        this.x = shop.getX();
        this.y = shop.getY();
        this.placeUrl = shop.getPlaceUrl();

        this.shopCategories = shop.getShopCategoryMaps().stream()
                .map(map -> map.getCategory().getName())
                .collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    public static class CategoryDto {
        private Long id;
        private String name;
    }
}
