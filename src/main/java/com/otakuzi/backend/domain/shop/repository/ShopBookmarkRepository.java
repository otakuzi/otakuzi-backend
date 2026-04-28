package com.otakuzi.backend.domain.shop.repository;

import com.otakuzi.backend.domain.shop.entity.Shop;
import com.otakuzi.backend.domain.shop.entity.ShopBookmark;
import com.otakuzi.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopBookmarkRepository extends JpaRepository<ShopBookmark, Long> {

    boolean existsByUserAndShop(User user, Shop shop);

    Optional<ShopBookmark> findByUserAndShop(User user, Shop shop);

    List<ShopBookmark> findAllByUserId(Long userId);
}
