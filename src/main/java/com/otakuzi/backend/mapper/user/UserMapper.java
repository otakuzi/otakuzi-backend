package com.otakuzi.backend.mapper.user;

import com.otakuzi.backend.dto.user.UserResponse;
import com.otakuzi.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy =  ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse toResponse(User user);
}