package com.otakuzi.backend.repository.community;

import com.otakuzi.backend.entity.community.CommunityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityCategoryRepository extends JpaRepository<CommunityCategory, Integer> {

}
