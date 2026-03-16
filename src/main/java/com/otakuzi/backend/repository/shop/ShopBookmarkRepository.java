package com.otakuzi.backend.repository.shop;

import com.otakuzi.backend.entity.shop.Shop;
import com.otakuzi.backend.entity.shop.ShopBookmark;
import com.otakuzi.backend.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopBookmarkRepository extends JpaRepository<ShopBookmark, Long> {

    boolean existsByUserAndShop(User user, Shop shop);

    Optional<ShopBookmark> findByUserAndShop(User user, Shop shop);

    List<ShopBookmark> findAllByUserId(Long userId);
}
