package com.otakuzi.backend.repository.community;

import com.otakuzi.backend.entity.community.CommunityPost;

import java.util.List;

public interface CommunityPostRepositoryCustom {
    List<CommunityPost> searchByFilters(String keyword, Integer categoryId);
}
