package com.otakuzi.backend.dto.shop;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopResponse {
    private long shopId;
    private String name;
    private List<String> shopCategories;
    private String phone;
    private String addressName;
    private String roadAddressName;
    private String x;  // 경도(longitude)
    private String y;  // 위도(latitude)
    private String placeUrl;
}
