package com.otakuzi.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "shop_category")
public class ShopCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_category_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public ShopCategory(String name) {
        this.name = name;
    }
}
