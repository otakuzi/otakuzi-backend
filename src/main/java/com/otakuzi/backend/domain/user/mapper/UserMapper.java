package com.otakuzi.backend.domain.user.mapper;

import com.otakuzi.backend.domain.user.dto.UserResponse;
import com.otakuzi.backend.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy =  ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse toResponse(User user);
}