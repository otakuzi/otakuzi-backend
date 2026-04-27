package com.otakuzi.backend.mapper.community;

import com.otakuzi.backend.entity.community.CommunityPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class CommunityPostMapper {

    @Mapping()
    CommunityPost toEntity(CommuniyPost)
}
