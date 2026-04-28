package com.otakuzi.backend.domain.shop.entity;

import com.otakuzi.backend.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ShopBookmarkTest {

    @Test
    @DisplayName("샵 북마크 객체 정상 생성되는지 확인")
    void createShopBookmark() {

        User user = new User();
        Shop shop = new Shop();

        ShopBookmark bookmark = new ShopBookmark(user, shop);

        assertThat(bookmark.getUser()).isEqualTo(user);
        assertThat(bookmark.getShop()).isEqualTo(shop);
    }
}
