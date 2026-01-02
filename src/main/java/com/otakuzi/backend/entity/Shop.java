package com.otakuzi.backend.entity;

import com.otakuzi.backend.entity.common.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shops")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop extends BaseTime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;
    
    @Column(name = "place_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShopCategoryMap> shopCategoryMaps = new ArrayList<>();
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "address_name", nullable = false)
    private String addressName;
    
    @Column(name = "road_address_name")
    private String roadAddressName;

    @Column(name = "x", nullable = false)
    private String x;  // 경도(longitude)
    
    @Column(name = "y", nullable = false)
    private String y;  // 위도(latitude)
    
    @Column(name = "place_url")
    private String placeUrl;

    // 카테고리 연결 편의 메서드
    public void addCategory(ShopCategory category) {
        // 1. 연결 엔티티(Map) 생성 (나(this)와 카테고리를 연결)
        ShopCategoryMap map = new ShopCategoryMap(this, category);

        // 2. 내 리스트에 추가
        this.shopCategoryMaps.add(map);
    }
}