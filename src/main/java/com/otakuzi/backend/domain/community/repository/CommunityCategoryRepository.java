package com.otakuzi.backend.domain.community.repository;

import com.otakuzi.backend.domain.community.entity.CommunityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityCategoryRepository extends JpaRepository<CommunityCategory, Integer> {

}
