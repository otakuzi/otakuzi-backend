package com.otakuzi.backend.service.shop;

import com.otakuzi.backend.dto.shop.ShopBookmarkResponse;
import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.ShopBookmark;
import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.global.base.BaseServiceTest;
import com.otakuzi.backend.mapper.shop.ShopMapper;
import com.otakuzi.backend.repository.ShopBookmarkRepository;
import com.otakuzi.backend.repository.ShopRepository;
import com.otakuzi.backend.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

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
        // ==========================================
        // [1] given: 부모가 준 헬퍼 메서드로 1초 만에 데이터 세팅 완료
        // ==========================================
        Long userId = 1L;

        // 1-1. 코드가 미치도록 깔끔해졌습니다!
        User user = createMockUser(userId, "테스트유저");
        Shop shop = createMockShop(10L, "홍대 굿즈샵", "홍대");

        ShopBookmark bookmark = new ShopBookmark(user, shop);

        // 1-2. 가짜 레포지토리 지시
        given(shopBookmarkRepository.findAllByUserId(userId))
                .willReturn(List.of(bookmark));

        // 1-3. 가짜 매퍼 지시
        ShopBookmarkResponse mockResponse = ShopBookmarkResponse.builder()
                .shopId(10L)
                .shopName("홍대 굿즈샵")
                .address("홍대")
                .build();
        given(shopMapper.toBookmarkResponse(shop)).willReturn(mockResponse);

        // ==========================================
        // [2] when: 실제 서비스 로직 실행
        // ==========================================
        // 🚨 [TDD] 여전히 이 부분에 '빨간 줄'이 떠 있어야 정상입니다.
        List<ShopBookmarkResponse> responses = shopBookmarkService.getMyBookmarks(userId);

        // ==========================================
        // [3] then: 검증
        // ==========================================
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getShopName()).isEqualTo("홍대 굿즈샵");
    }
}
