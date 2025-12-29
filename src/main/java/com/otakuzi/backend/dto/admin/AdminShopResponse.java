package com.otakuzi.backend.dto.admin;

import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.ShopCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AdminShopResponse {
    private Long id;
    private String name;
    private String phone;
    private String addressName;
    private String roadAddressName;
    private String x;
    private String y;
    private String placeUrl;

    private List<String> categoryNames;
    private List<Long> categoryIds;

    public AdminShopResponse(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.phone = shop.getPhone();
        this.addressName = shop.getAddressName();
        this.roadAddressName = shop.getRoadAddressName();
        this.x = shop.getX();
        this.y = shop.getY();
        this.placeUrl = shop.getPlaceUrl();

        this.categoryNames = shop.getShopCategoryMaps().stream()
                .map(map -> map.getCategory().getName())
                .collect(Collectors.toList());

        this.categoryIds = shop.getShopCategoryMaps().stream()
                .map(map -> map.getCategory().getId())
                .collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    public static class CategoryDto {
        private Long id;
        private String name;

        public CategoryDto(ShopCategory category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }
}
