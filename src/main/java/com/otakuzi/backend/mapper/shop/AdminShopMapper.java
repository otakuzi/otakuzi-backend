package com.otakuzi.backend.mapper.shop;

import com.otakuzi.backend.dto.shop.AdminShopCreateRequest;
import com.otakuzi.backend.dto.shop.AdminShopResponse;
import com.otakuzi.backend.dto.shop.AdminShopUpdateRequest;
import com.otakuzi.backend.dto.shop.ShopCategoryResponse;
import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.ShopCategoryMap;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminShopMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shopCategoryMaps", ignore = true)
    Shop toEntity(AdminShopCreateRequest request);

    @Mapping(source = "shopCategoryMaps", target = "categories")
    AdminShopResponse toAdminResponse(Shop shop);

    @Mapping(source = "category.id", target = "id")
    @Mapping(source = "category.name", target = "name")
    ShopCategoryResponse toCategoryResponse(ShopCategoryMap map);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shopCategoryMaps", ignore = true)
    void updateFromDto(AdminShopUpdateRequest dto, @MappingTarget Shop shop);

    List<AdminShopResponse> toAdminResponseList(List<Shop> shops);
}
