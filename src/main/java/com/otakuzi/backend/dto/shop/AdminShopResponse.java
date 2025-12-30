package com.otakuzi.backend.dto.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

    private List<ShopCategoryResponse> categories;
}
