package com.otakuzi.backend.domain.community.repository;

import com.otakuzi.backend.domain.community.entity.CommunityCategory;
import com.otakuzi.backend.domain.community.entity.CommunityPost;
import com.otakuzi.backend.domain.community.repository.CommunityCategoryRepository;
import com.otakuzi.backend.domain.community.repository.CommunityPostRepository;
import com.otakuzi.backend.domain.user.entity.User;
import com.otakuzi.backend.global.base.BaseRepositoryTest;
import com.otakuzi.backend.global.constant.UserType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CommunityPostRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private CommunityPostRepository communityPostRepository;

    @Autowired
    private CommunityCategoryRepository communityCategoryRepository;

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
    @DisplayName("제목/내용만 검색하거나 카테고리와 함께 검색할 수 있다.")
    void searchByFilters() {

        User user = createAndSaveUser("테스터", "otakuzi@otakuim.com", UserType.USER);

        CommunityCategory cat1 = communityCategoryRepository.save(new CommunityCategory(null, "자유게시판", "자유로운 곳"));
        CommunityCategory cat2 = communityCategoryRepository.save(new CommunityCategory(null, "정보게시판", "정보를 나누는 곳"));

        CommunityPost post1 = createAndSaveCommunityPost(user.getId(), 1, "otakuzi 일빠", "첫게시글");
        CommunityPost post2 = createAndSaveCommunityPost(user.getId(), 2, "titled", "the content otakuzi");

        List<CommunityPost> result1 = communityPostRepository.searchByFilters("title", null);
        assertThat(result1).hasSize(1);
        assertThat(result1.get(0).getTitle()).contains("title");

        List<CommunityPost> result2 = communityPostRepository.searchByFilters(null, 2);
        assertThat(result2).hasSize(1);
        assertThat(result2.get(0).getCategoryId()).isEqualTo(2);

        List<CommunityPost> result3 = communityPostRepository.searchByFilters("otakuzi", null);
        assertThat(result3).hasSize(2);
    }
}
