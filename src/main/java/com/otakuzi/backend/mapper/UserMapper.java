package com.otakuzi.backend.mapper;

import com.otakuzi.backend.dto.admin.AdminUserResponse;
import com.otakuzi.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    // Entity -> DTO 변환
    AdminUserResponse toAdminResponse(User user);
}
