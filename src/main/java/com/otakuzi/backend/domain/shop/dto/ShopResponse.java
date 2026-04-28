package com.otakuzi.backend.domain.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShopResponse {
    private Long id;
    private String name;
    private String phone;
    private String addressName;
    private String roadAddressName;
    private String x;  // 경도(longitude)
    private String y;  // 위도(latitude)
    private String placeUrl;

    private List<ShopCategoryResponse> categories;
}
