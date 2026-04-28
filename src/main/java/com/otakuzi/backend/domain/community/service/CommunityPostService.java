package com.otakuzi.backend.domain.community.service;

import com.otakuzi.backend.domain.community.dto.CommunityPostCreateRequest;
import com.otakuzi.backend.domain.community.repository.CommunityCategoryRepository;
import com.otakuzi.backend.domain.community.repository.CommunityPostRepository;
import com.otakuzi.backend.domain.user.repository.UserRepository;
import com.otakuzi.backend.global.api.ApiResponse;
import com.otakuzi.backend.global.config.auth.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostService {

    private final CommunityPostRepository communityPostRepository;
    private final CommunityCategoryRepository communityCategoryRepository;
    private final UserRepository userRepository;

    public ApiResponse<Void> CreateCommunityPost(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody @Valid CommunityPostCreateRequest request
    ) {
        return ApiResponse.success();
    }
}
