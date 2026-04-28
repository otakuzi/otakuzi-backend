package com.otakuzi.backend.domain.shop.service;

import com.otakuzi.backend.domain.shop.dto.ShopBookmarkResponse;
import com.otakuzi.backend.domain.shop.entity.Shop;
import com.otakuzi.backend.domain.shop.entity.ShopBookmark;
import com.otakuzi.backend.domain.user.entity.User;
import com.otakuzi.backend.domain.shop.mapper.ShopMapper;
import com.otakuzi.backend.domain.shop.repository.ShopBookmarkRepository;
import com.otakuzi.backend.domain.shop.repository.ShopRepository;
import com.otakuzi.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopBookmarkService {

    private final ShopBookmarkRepository shopBookmarkRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    public List<ShopBookmarkResponse> getMyBookmarks(Long userId) {
        List<ShopBookmark> bookmarks = shopBookmarkRepository.findAllByUserId(userId);

        return bookmarks.stream()
                .map(bookmark -> {
                    return shopMapper.toBookmarkResponse(bookmark.getShop());
                })
                .collect(Collectors.toList());
    }

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
