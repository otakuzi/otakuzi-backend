package com.otakuzi.backend.repository.community;

import com.otakuzi.backend.entity.community.CommunityPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.otakuzi.backend.entity.community.QCommunityPost.communityPost;

@RequiredArgsConstructor
public class CommunityPostRepositoryImpl implements CommunityPostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommunityPost> searchByFilters(String keyword, Integer categoryId) {

        return queryFactory
                .selectFrom(communityPost)
                .distinct()
                .where(
                    eqCategory(categoryId),
                    containsKeyword(keyword),
                    communityPost.isDeleted.isFalse()
                )
                .orderBy(communityPost.createdAt.desc())
                .fetch();
    }

    private BooleanExpression eqCategory(Integer categoryId) {
        return categoryId != null ? communityPost.categoryId.eq(categoryId) : null;
    }

    private BooleanExpression containsKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        return communityPost.title.contains(keyword)
                .or(communityPost.content.contains(keyword));
    }
}
