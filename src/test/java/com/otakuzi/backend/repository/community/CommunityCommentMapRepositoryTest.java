package com.otakuzi.backend.repository.community;

import com.otakuzi.backend.entity.community.CommunityCategory;
import com.otakuzi.backend.entity.community.CommunityCommentMap;
import com.otakuzi.backend.entity.community.CommunityPost;
import com.otakuzi.backend.entity.user.User;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
class CommunityCommentMapRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private CommunityCommentMapRepository communityCommentMapRepository;

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
    @DisplayName("익명 번호 부여 로직 검증")
    void anonymousNumberLogicTest() {

        User author = createAndSaveUser("작성자", "user1@test.com", UserType.USER);
        User commenter2 = createAndSaveUser("댓글러2", "user2@test.com", UserType.USER);
        User commenter3 = createAndSaveUser("댓글러3", "user3@test.com", UserType.USER);

        CommunityCategory cat = communityCategoryRepository.save(new CommunityCategory(null, "자유게시판", "자유롭게 쓰세요."));
        CommunityPost post = createAndSaveCommunityPost(author.getId(), cat.getId());

        Optional<CommunityCommentMap> existingMap1 = communityCommentMapRepository.findByPostIdAndUserId(post.getId(), commenter2.getId());
        Optional<CommunityCommentMap> existingMap2 = communityCommentMapRepository.findByPostIdAndUserId(post.getId(), commenter3.getId());

        int commenterNumber1 = existingMap1.map(CommunityCommentMap::getAnonymousNumber)
                .orElseGet(() -> {
                    int nextNum = communityCommentMapRepository.findMaxNumberByPostId(post.getId()) + 1;

                    return communityCommentMapRepository.save(CommunityCommentMap.builder()
                            .postId(post.getId())
                            .userId(commenter2.getId())
                            .anonymousNumber(nextNum)
                            .build()).getAnonymousNumber();
                });

        String commenterNick = (commenter2.getId().equals(post.getUserId())) ? "익명(글쓴이)" : "익명" + commenterNumber1;

        assertThat(commenterNick).isEqualTo("익명1");

        int authorNumber = communityCommentMapRepository.findByPostIdAndUserId(post.getId(), author.getId())
                .map(CommunityCommentMap::getAnonymousNumber)
                .orElseGet(() -> {
                    int nextNum = communityCommentMapRepository.findMaxNumberByPostId(post.getId()) + 1;

                    return communityCommentMapRepository.save(CommunityCommentMap.builder()
                            .postId(post.getId())
                            .userId(author.getId())
                            .anonymousNumber(nextNum)
                            .build()).getAnonymousNumber();
                });

        String authorNick = (author.getId().equals(post.getUserId())) ? "익명(글쓴이)" : "익명" + authorNumber;

        assertThat(authorNumber).isEqualTo(2);
        assertThat(authorNick).isEqualTo("익명(글쓴이)");

        int repeatAuthorNumber = communityCommentMapRepository.findByPostIdAndUserId(post.getId(), author.getId())
                .map(CommunityCommentMap::getAnonymousNumber)
                .orElse(-1);

        assertThat(repeatAuthorNumber).isEqualTo(2);
    }

}
