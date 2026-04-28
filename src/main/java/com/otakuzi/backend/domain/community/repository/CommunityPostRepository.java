package com.otakuzi.backend.domain.community.repository;

import com.otakuzi.backend.domain.community.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long>, CommunityPostRepositoryCustom {

}
