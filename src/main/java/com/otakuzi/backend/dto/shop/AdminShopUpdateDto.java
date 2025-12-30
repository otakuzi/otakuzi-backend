package com.otakuzi.backend.dto.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString // 디버깅
public class AdminShopUpdateDto {

    private String name;
    private String phone;
    private String addressName;
    private String roadAddressName;
    private String x;
    private String y;
    private String placeUrl;

    private List<Long> categoryIds;
}
