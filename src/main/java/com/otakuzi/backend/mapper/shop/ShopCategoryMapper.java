package com.otakuzi.backend.mapper.shop;

import com.otakuzi.backend.dto.shop.AdminShopResponse;
import com.otakuzi.backend.dto.shop.ShopCategoryResponse;
import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.ShopCategory;
import com.otakuzi.backend.entity.ShopCategoryMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopCategoryMapper {

    ShopCategoryResponse toCategoryResponse(ShopCategoryMap map);

    List<AdminShopResponse> toAdminResponseList(List<Shop> shops);

    ShopCategoryResponse toResponse(ShopCategory category);

    List<ShopCategoryResponse> toResponseList(List<ShopCategory> categories);

}
