package com.otakuzi.backend.repository.shop;

import com.otakuzi.backend.entity.shop.Shop;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.otakuzi.backend.entity.shop.QShop.shop;
import static com.otakuzi.backend.entity.shop.QShopCategoryMap.shopCategoryMap;
import static com.otakuzi.backend.entity.shop.QShopCategory.shopCategory;

@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Shop> searchByFilters(String name, List<String> categories) {

        return queryFactory
                .selectFrom(shop)
                .distinct()
                .leftJoin(shop.shopCategoryMaps, shopCategoryMap)
                .leftJoin(shopCategoryMap.category, shopCategory)
                .where(
                    containsName(name),
                    inCategories(categories)
                )
                .fetch();
    }

    private BooleanExpression containsName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return shop.name.contains(name);
    }

    private BooleanExpression inCategories(List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            return null;
        }
        return shopCategory.name.in(categories);
    }
}