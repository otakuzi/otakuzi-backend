package com.otakuzi.backend.domain.shop.mapper;

import com.otakuzi.backend.domain.shop.dto.ShopBookmarkResponse;
import com.otakuzi.backend.domain.shop.dto.ShopCategoryResponse;
import com.otakuzi.backend.domain.shop.dto.ShopResponse;
import com.otakuzi.backend.domain.shop.entity.Shop;
import com.otakuzi.backend.domain.shop.entity.ShopCategoryMap;
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
    @Mapping(source = "shopCategoryMaps", target = "categories")
    ShopBookmarkResponse toBookmarkResponse(Shop shop);
}
