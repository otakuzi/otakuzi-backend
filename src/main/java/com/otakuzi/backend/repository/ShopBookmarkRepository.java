package com.otakuzi.backend.repository;

import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.ShopBookmark;
import com.otakuzi.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopBookmarkRepository extends JpaRepository<ShopBookmark, Long> {

    boolean existsByUserAndShop(User user, Shop shop);

    Optional<ShopBookmark> findByUserAndShop(User user, Shop shop);
}
