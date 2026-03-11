package com.otakuzi.backend.dto.shop;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ShopBookmarkResponse {
    private Long shopId;
    private String shopName;
    private String phone;
    private String address;
    private List<ShopCategoryResponse> categories;
}
