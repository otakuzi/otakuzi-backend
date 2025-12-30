package com.otakuzi.backend.dto.shop;

import com.otakuzi.backend.entity.Shop;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
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
