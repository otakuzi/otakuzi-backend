package com.otakuzi.backend.global.base;

import com.otakuzi.backend.entity.Shop;
import com.otakuzi.backend.entity.User;
import com.otakuzi.backend.global.constant.UserType;
import com.otakuzi.backend.repository.ShopRepository;
import com.otakuzi.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public abstract class BaseRepositoryTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ShopRepository shopRepository;

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
                .addressName(addressName)
                .x("126.9242") // 테스트용 임의의 X 좌표
                .y("37.5556")  // 테스트용 임의의 Y 좌표
                .build();
        shop.setName(name); // 기존에 Setter로 이름을 넣으셨던 방식 그대로!

        return shopRepository.save(shop); // 만들자마자 DB에 쏙!
    }
}