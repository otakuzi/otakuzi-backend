package com.otakuzi.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@Table(name = "shops")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private BigInteger shopId;
    
    @Column(name = "place_name", nullable = false)
    private String placeName;
    
    @Column(name = "category_name")
    private String categoryName;
    
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
}