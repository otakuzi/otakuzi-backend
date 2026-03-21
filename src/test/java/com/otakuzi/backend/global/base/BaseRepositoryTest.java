package com.otakuzi.backend.global.base;

import com.otakuzi.backend.entity.community.CommunityPost;
import com.otakuzi.backend.entity.shop.Shop;
import com.otakuzi.backend.entity.user.User;
import com.otakuzi.backend.global.constant.UserType;
import com.otakuzi.backend.repository.community.CommunityPostRepository;
import com.otakuzi.backend.repository.shop.ShopRepository;
import com.otakuzi.backend.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public abstract class BaseRepositoryTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ShopRepository shopRepository;

    @Autowired
    protected CommunityPostRepository communityPostRepository;

    protected User createAndSaveUser(String nickname, String email, UserType type) {
        User user = User.builder()
                .nickname(nickname)
                .email(email)
                .type(type)
                .provider("KAKAO")
                .providerId("test_provider_" + nickname)
                .build();
        return userRepository.save(user); // 만들고 바로 DB 저장!
    }

    protected Shop createAndSaveShop(String name, String addressName) {
        Shop shop = Shop.builder()
                .name(name)
                .addressName(addressName)
                .x("126.9242")
                .y("37.5556")
                .build();

        return shopRepository.save(shop);
    }

    // 제목, 내용, 카테고리 없는 생성 테스트
    protected CommunityPost createAndSaveCommunityPost(Long userId, Integer categoryId) {
        CommunityPost communityPost = CommunityPost.builder()
                .title("오타쿠지 짱")
                .content("오타쿠지 미쳤는데요?(P)")
                .userId(userId)
                .categoryId(categoryId)
                .isDeleted(false)
                .build();

        return communityPostRepository.save(communityPost);
    }

    // 제목, 내용, 카테고리 생성 테스트
    protected CommunityPost createAndSaveCommunityPost(Long userId, Integer categoryId, String title, String content) {
        CommunityPost communityPost = CommunityPost.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .categoryId(categoryId)
                .isDeleted(false)
                .build();

        return communityPostRepository.save(communityPost);
    }
}