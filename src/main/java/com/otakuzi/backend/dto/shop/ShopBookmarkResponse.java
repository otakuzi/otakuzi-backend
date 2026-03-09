package com.otakuzi.backend.dto.shop;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopBookmarkResponse {
    private Long shopId;
    private String shopName;
    private String address;
}
