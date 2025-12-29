package com.otakuzi.backend.repository;

import com.otakuzi.backend.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom {

}