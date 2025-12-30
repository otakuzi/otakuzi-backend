package com.otakuzi.backend.mapper.shop;

import com.otakuzi.backend.dto.shop.AdminShopCreateRequest;
import com.otakuzi.backend.dto.shop.AdminShopResponse;
import com.otakuzi.backend.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopMapper {

    @Mapping(target = "id", ignore = true)
    Shop toEntity(AdminShopCreateRequest request);

    AdminShopResponse toAdminResponse(Shop shop);

    List<AdminShopResponse> toAdminResponseList(List<Shop> shops);
}
