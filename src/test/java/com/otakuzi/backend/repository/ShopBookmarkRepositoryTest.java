package com.otakuzi.backend.repository;

import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.ShopBookmark;
import com.otakuzi.backend.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class ShopBookmarkRepositoryTest {

    @Autowired
    private ShopBookmarkRepository shopBookmarkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopRepository shopRepository;

    @TestConfiguration
    static class TestConfig {

        @PersistenceContext
        private EntityManager entityManager;

        @Bean
        public JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(entityManager);
        }
    }

    @Test
    @DisplayName("북마크를 DB에 저장하고 불러올 수 있다")
    void saveAndFindBookmark() {

        Long userId = 1L;
        User user = User.builder()
                .nickname("otakuzi")
                .email("test@otakuzi.com")
                .type(com.otakuzi.backend.global.constant.UserType.USER)
                .provider("KAKAO")
                .providerId("test")
                .build();

        userRepository.save(user);

        Long shopId = 2L;
        Shop shop = Shop.builder()
                .addressName("테스트 주소")
                .x("1")
                .y("1")
                .build();
        shop.setName("테스트 굿즈샵");
        shopRepository.save(shop);

        ShopBookmark bookmark = new ShopBookmark(user, shop);
        ShopBookmark savedBookmark = shopBookmarkRepository.save(bookmark);

        assertThat(savedBookmark.getId()).isNotNull();
        assertThat(savedBookmark.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedBookmark.getShop().getId()).isEqualTo(shop.getId());

        System.out.println("생성된 북마크 ID: " + savedBookmark.getId());
    }
}
