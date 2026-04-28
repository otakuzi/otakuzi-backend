package com.otakuzi.backend.global.base;

import com.otakuzi.backend.domain.community.entity.CommunityPost;
import com.otakuzi.backend.domain.shop.entity.Shop;
import com.otakuzi.backend.domain.user.entity.User;
import com.otakuzi.backend.global.constant.UserType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public abstract class BaseServiceTest {

    // ==========================================
    // ⭐ [가짜 객체 공장] DB를 안 쓰므로 ID를 강제로 꽂아넣는 헬퍼 메서드들
    // ==========================================

    protected User createMockUser(Long id, String nickname) {
        User user = User.builder()
                .nickname(nickname)
                .email("test@otakuzi.com")
                .type(UserType.USER)
                .build();

        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }

    protected Shop createMockShop(Long id, String name, String addressName) {
        Shop shop = Shop.builder()
                .addressName(addressName)
                .x("126.9242")
                .y("37.5556")
                .build();
        shop.setName(name);

        ReflectionTestUtils.setField(shop, "id", id);
        return shop;
    }

    protected CommunityPost createMockPost(Long id, Long userId, String title, String content, Integer categoryId) {
        CommunityPost post = CommunityPost.builder()
                .categoryId(categoryId)
                .title(title)
                .content(content)
                .userId(userId)
                .build();

        ReflectionTestUtils.setField(post, "id", id);
        return post;
    }
}