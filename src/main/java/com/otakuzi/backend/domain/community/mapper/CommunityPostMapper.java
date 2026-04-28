package com.otakuzi.backend.domain.community.mapper;

import com.otakuzi.backend.domain.community.entity.CommunityPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class CommunityPostMapper {

//    @Mapping()
//    CommunityPost toEntity(CommuniyPost)
}
