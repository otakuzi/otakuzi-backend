package com.otakuzi.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "shop_category_map")
public class ShopCategoryMap {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_category_id")
    private ShopCategory shopCategory;

    public ShopCategoryMap(Shop shop, ShopCategory shopCategory) {
        this.shop = shop;
        this.shopCategory = shopCategory;
    }

}
