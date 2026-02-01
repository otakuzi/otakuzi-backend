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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        User user = User.builder()
                .nickname("otakuzi")
                .email("test@otakuzi.com")
                .type(com.otakuzi.backend.global.constant.UserType.USER)
                .provider("KAKAO")
                .providerId("test")
                .build();

        userRepository.save(user);

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

    @Test
    @DisplayName("북마크를 삭제할 수 있다.")
    void deleteBookmark() {

        User user = User.builder()
                .nickname("otakuzi")
                .email("test@otakuzi.com")
                .type(com.otakuzi.backend.global.constant.UserType.USER)
                .provider("KAKAO")
                .providerId("test")
                .build();

        userRepository.save(user);

        Shop shop = Shop.builder()
                .addressName("테스트 주소")
                .x("1")
                .y("1")
                .build();
        shop.setName("삭제할 굿즈샵");
        shopRepository.save(shop);

        ShopBookmark bookmark = new ShopBookmark(user, shop);
        shopBookmarkRepository.save(bookmark);

        shopBookmarkRepository.delete(bookmark);

        boolean isPresent = shopBookmarkRepository.findById(bookmark.getId()).isPresent();

        assertThat(isPresent).isFalse();
    }

    @Test
    @DisplayName("같은 샵을 중복으로 등록하면 에러가 발생해야 한다.")
    void duplicateBookmarkThrowsException() {

        User user = User.builder()
                .nickname("duplicateUser")
                .email("dup@otakuzi.com")
                .type(com.otakuzi.backend.global.constant.UserType.USER)
                .provider("KAKAO")
                .providerId("test")
                .build();

        userRepository.save(user);

        Shop shop = Shop.builder()
                .addressName("테스트 주소")
                .x("1")
                .y("1")
                .build();
        shop.setName("인기짱 굿즈샵");
        shopRepository.save(shop);

        shopBookmarkRepository.save(new ShopBookmark(user, shop));

        assertThrows(DataIntegrityViolationException.class, () -> {
            shopBookmarkRepository.save(new ShopBookmark(user, shop));
        });


    }
}
