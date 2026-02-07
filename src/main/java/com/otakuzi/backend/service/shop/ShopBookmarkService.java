package com.otakuzi.backend.service.shop;

import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.ShopBookmark;
import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.repository.ShopBookmarkRepository;
import com.otakuzi.backend.repository.ShopRepository;
import com.otakuzi.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopBookmarkService {

    private final ShopBookmarkRepository shopBookmarkRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    @Transactional
    public void toggleBookmark(Long userId, Long shopId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장을 찾을 수 없습니다,"));

        Optional<ShopBookmark> bookmarkOptional = shopBookmarkRepository.findByUserAndShop(user, shop);

        if (bookmarkOptional.isPresent()) {
            shopBookmarkRepository.delete(bookmarkOptional.get());
        } else {
            ShopBookmark newBookmark = new ShopBookmark(user, shop);
            shopBookmarkRepository.save(newBookmark);
        }
    }
}
