package com.otakuzi.backend.service.community;

import com.otakuzi.backend.global.base.BaseServiceTest;
import com.otakuzi.backend.repository.community.CommunityCategoryRepository;
import com.otakuzi.backend.repository.community.CommunityPostRepository;
import com.otakuzi.backend.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommunityPostServiceTest extends BaseServiceTest {

    @Mock
    private CommunityPostRepository communityPostRepository;

    @Mock
    private CommunityCategoryRepository communityCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommunityPostService communityPostService;

    @Test
    @DisplayName("게시글이 작성자, 카테고리 ID를 포함하여 올바르게 저장된다.")
    void createPost() {

    }

}
