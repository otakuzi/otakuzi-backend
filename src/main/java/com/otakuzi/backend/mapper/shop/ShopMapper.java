package com.otakuzi.backend.mapper.shop;

import com.otakuzi.backend.dto.shop.ShopBookmarkResponse;
import com.otakuzi.backend.dto.shop.ShopCategoryResponse;
import com.otakuzi.backend.dto.shop.ShopResponse;
import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.ShopCategoryMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopMapper {

    @Mapping(source = "category.id", target = "id")
    @Mapping(source = "category.name", target = "name")
    ShopCategoryResponse toShopCategoryResponse(ShopCategoryMap map);

    @Mapping(source = "shopCategoryMaps", target = "categories")
    ShopResponse toResponse(Shop shop);

    List<ShopResponse> toResponseList(List<Shop> shops);

    @Mapping(source = "id", target = "shopId")
    @Mapping(source = "name", target = "shopName")
    @Mapping(source = "addressName", target = "address")
    ShopBookmarkResponse toBookmarkResponse(Shop shop);
}
