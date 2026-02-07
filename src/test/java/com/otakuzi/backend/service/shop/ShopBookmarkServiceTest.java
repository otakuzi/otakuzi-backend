package com.otakuzi.backend.service.shop;

import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.ShopBookmark;
import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.repository.ShopBookmarkRepository;
import com.otakuzi.backend.repository.ShopRepository;
import com.otakuzi.backend.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ShopBookmarkServiceTest {

    @Mock
    private ShopBookmarkRepository shopBookmarkRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopBookmarkService shopBookmarkService;

    @Test
    @DisplayName("북마크가 없을 때 누르면 저장")
    void toggle_save() {

        Long userId = 1L;
        Long shopId = 2L;
        User user = User.builder()
                .nickname("saveTestUser")
                .email("test@otakuzi.com")
                .type(com.otakuzi.backend.global.constant.UserType.USER)
                .provider("KAKAO")
                .providerId("test")
                .build();

        Shop shop = Shop.builder()
                .addressName("저장 테스트 주소")
                .x("1")
                .y("1")
                .build();

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(shopRepository.findById(shopId)).willReturn(Optional.of(shop));

        given(shopBookmarkRepository.findByUserAndShop(user, shop)).willReturn(Optional.empty());

        shopBookmarkService.toggleBookmark(userId, shopId);

        verify(shopBookmarkRepository, times(1)).save(any(ShopBookmark.class));
    }

    @Test
    @DisplayName("북마크가 있을 때 누르면 삭제")
    void toggle_delete() {

        Long userId = 1L;
        Long shopId = 2L;
        User user = User.builder()
                .nickname("saveTestUser")
                .email("test@otakuzi.com")
                .type(com.otakuzi.backend.global.constant.UserType.USER)
                .provider("KAKAO")
                .providerId("test")
                .build();

        Shop shop = Shop.builder()
                .addressName("저장 테스트 주소")
                .x("1")
                .y("1")
                .build();

        ShopBookmark bookmark = new ShopBookmark(user, shop);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(shopRepository.findById(shopId)).willReturn(Optional.of(shop));

        given(shopBookmarkRepository.findByUserAndShop(user, shop)).willReturn(Optional.of(bookmark));

        shopBookmarkService.toggleBookmark(userId, shopId);

        verify(shopBookmarkRepository, times(1)).delete(bookmark);
    }
}
