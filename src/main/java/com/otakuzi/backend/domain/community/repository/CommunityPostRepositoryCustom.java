package com.otakuzi.backend.domain.community.repository;

import com.otakuzi.backend.domain.community.entity.CommunityPost;

import java.util.List;

public interface CommunityPostRepositoryCustom {
    List<CommunityPost> searchByFilters(String keyword, Integer categoryId);
}
