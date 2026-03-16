package com.otakuzi.backend.mapper.user;

import com.otakuzi.backend.dto.user.AdminUserResponse;
import com.otakuzi.backend.dto.user.AdminUserUpdateRequest;
import com.otakuzi.backend.entity.user.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminUserMapper {
    // Entity -> DTO 변환
    AdminUserResponse toAdminResponse(User user);

    List<AdminUserResponse> toAdminResponseList(List<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdminUserUpdateRequest dto, @MappingTarget User user);
}
