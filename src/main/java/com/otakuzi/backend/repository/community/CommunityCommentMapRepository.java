package com.otakuzi.backend.repository.community;

import com.otakuzi.backend.entity.community.CommunityCommentMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommunityCommentMapRepository extends JpaRepository<CommunityCommentMap, Long> {

    Optional<CommunityCommentMap> findByPostIdAndUserId(Long postId, Long userId);

    @Query("SELECT COALESCE(MAX(m.anonymousNumber), 0) FROM CommunityCommentMap m WHERE m.postId = :postId")
    int findMaxNumberByPostId(@Param("postId") Long postId);
}
