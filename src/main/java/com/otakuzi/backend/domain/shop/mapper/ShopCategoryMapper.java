package com.otakuzi.backend.domain.shop.mapper;

import com.otakuzi.backend.domain.shop.dto.AdminShopResponse;
import com.otakuzi.backend.domain.shop.dto.ShopCategoryResponse;
import com.otakuzi.backend.domain.shop.entity.Shop;
import com.otakuzi.backend.domain.shop.entity.ShopCategory;
import com.otakuzi.backend.domain.shop.entity.ShopCategoryMap;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopCategoryMapper {

    ShopCategoryResponse toCategoryResponse(ShopCategoryMap map);

    List<AdminShopResponse> toAdminResponseList(List<Shop> shops);

    ShopCategoryResponse toResponse(ShopCategory category);

    List<ShopCategoryResponse> toResponseList(List<ShopCategory> categories);

}
