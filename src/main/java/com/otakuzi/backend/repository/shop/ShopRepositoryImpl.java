package com.otakuzi.backend.repository.shop;

import com.otakuzi.backend.entity.shop.Shop;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

// ★ Q클래스들은 static import로 가져오면 코드가 깔끔해집니다.
import static com.otakuzi.backend.entity.QShop.shop;
import static com.otakuzi.backend.entity.QShopCategoryMap.shopCategoryMap;
import static com.otakuzi.backend.entity.QShopCategory.shopCategory;

@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom {

    private final JPAQueryFactory queryFactory; // 아까 등록한 요리사

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
        // 이름이 없으면 null 반환 (쿼리에서 제외)
        if (name == null || name.isEmpty()) {
            return null;
        }
        // 있으면 LIKE %name% 조건 생성
        return shop.name.contains(name);
    }

    private BooleanExpression inCategories(List<String> categories) {
        // 카테고리 선택 안 했으면 null 반환 (쿼리에서 제외)
        if (categories == null || categories.isEmpty()) {
            return null;
        }
        // 있으면 IN (cat1, cat2) 조건 생성
        return shopCategory.name.in(categories);
    }
}