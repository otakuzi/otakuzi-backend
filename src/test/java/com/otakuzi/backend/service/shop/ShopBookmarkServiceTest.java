package com.otakuzi.backend.service.shop;

import com.otakuzi.backend.dto.shop.ShopBookmarkResponse;
import com.otakuzi.backend.entity.shop.Shop;
import com.otakuzi.backend.entity.shop.ShopBookmark;
import com.otakuzi.backend.entity.user.User;
import com.otakuzi.backend.global.base.BaseServiceTest;
import com.otakuzi.backend.mapper.shop.ShopMapper;
import com.otakuzi.backend.repository.shop.ShopBookmarkRepository;
import com.otakuzi.backend.repository.shop.ShopRepository;
import com.otakuzi.backend.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
@Transactional
class ShopBookmarkServiceTest extends BaseServiceTest {

    @Mock
    private ShopBookmarkRepository shopBookmarkRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopBookmarkService shopBookmarkService;

    @Mock
    private ShopMapper shopMapper;

    @Test
    @DisplayName("북마크가 없을 때 누르면 저장")
    void toggle_save() {

        Long userId = 1L;
        Long shopId = 2L;
        User user = createMockUser(userId, "저장 테스트 유저");
        Shop shop = createMockShop(shopId, "저장 테스트 샵", "여기저기어딘가");

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
        User user = createMockUser(userId, "삭제 테스트 유저");
        Shop shop = createMockShop(shopId, "삭제 테스트 샵", "여기저기어딘가");

        ShopBookmark bookmark = new ShopBookmark(user, shop);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(shopRepository.findById(shopId)).willReturn(Optional.of(shop));

        given(shopBookmarkRepository.findByUserAndShop(user, shop)).willReturn(Optional.of(bookmark));

        shopBookmarkService.toggleBookmark(userId, shopId);

        verify(shopBookmarkRepository, times(1)).delete(bookmark);
    }

    @Test
    @DisplayName("유저 ID로 내 북마크 목록을 조회하면 DTO 리스트로 반환된다")
    void getMyBookmarks() {

        Long userId = 1L;

        User user = createMockUser(userId, "테스트유저");
        Shop shop = createMockShop(10L, "홍대 굿즈샵", "홍대");

        ShopBookmark bookmark = new ShopBookmark(user, shop);

        given(shopBookmarkRepository.findAllByUserId(userId))
                .willReturn(List.of(bookmark));

        ShopBookmarkResponse mockResponse = ShopBookmarkResponse.builder()
                .shopId(10L)
                .shopName("홍대 굿즈샵")
                .address("홍대")
                .build();
        given(shopMapper.toBookmarkResponse(shop)).willReturn(mockResponse);

        List<ShopBookmarkResponse> responses = shopBookmarkService.getMyBookmarks(userId);

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getShopName()).isEqualTo("홍대 굿즈샵");
    }
}
