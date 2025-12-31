package com.otakuzi.backend.mapper.shop;

import com.otakuzi.backend.dto.shop.ShopResponse;
import com.otakuzi.backend.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopMapper {

    ShopResponse toResponse(Shop shop);

    List<ShopResponse> toResponseList(List<Shop> shops);
}
