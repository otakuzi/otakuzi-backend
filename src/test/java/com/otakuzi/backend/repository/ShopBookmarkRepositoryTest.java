package com.otakuzi.backend.repository;

import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.ShopBookmark;
import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.global.base.BaseRepositoryTest;
import com.otakuzi.backend.global.constant.UserType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShopBookmarkRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private ShopBookmarkRepository shopBookmarkRepository;

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

        User user = createAndSaveUser("저장 테스트 유저", "save@otakuzi.com", UserType.USER);
        userRepository.save(user);

        Shop shop = createAndSaveShop("저장 테스트 샵", "여기저기어딘가");
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

        User user = createAndSaveUser("삭제 테스트 유저", "delete@otakuzi.com", UserType.USER);
        userRepository.save(user);

        Shop shop = createAndSaveShop("삭제 테스트 샵", "여기저기어딘가");
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

        User user = createAndSaveUser("중복 테스트 유저", "duplicate@otakuzi.com", UserType.USER);

        userRepository.save(user);

        Shop shop = createAndSaveShop("중복 테스트 샵", "여기저기 어딘가");

        shopRepository.save(shop);

        shopBookmarkRepository.save(new ShopBookmark(user, shop));

        assertThrows(DataIntegrityViolationException.class, () -> {
            shopBookmarkRepository.save(new ShopBookmark(user, shop));
        });
    }

    @Test
    @DisplayName("특정 유저가 북마크한 모든 매장 목록을 조회할 수 있다")
    void findAllByUserId() {

        User savedUser = createAndSaveUser("조회테스트", "find@otakuzi.com", UserType.USER);

        Shop shop1 = createAndSaveShop("오타쿠지 홍대점", "서울시 마포구");
        Shop shop2 = createAndSaveShop("오타쿠지 강남점", "서울시 강남구");

        shopBookmarkRepository.save(new ShopBookmark(savedUser, shop1));
        shopBookmarkRepository.save(new ShopBookmark(savedUser, shop2));

        List<ShopBookmark> bookmarks = shopBookmarkRepository.findAllByUserId(savedUser.getId());

        assertThat(bookmarks).hasSize(2); // 2개 저장했으니 2개가 나와야 함!
    }
}
