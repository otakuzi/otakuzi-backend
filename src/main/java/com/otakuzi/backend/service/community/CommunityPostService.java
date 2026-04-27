package com.otakuzi.backend.service.community;

import com.otakuzi.backend.repository.community.CommunityCategoryRepository;
import com.otakuzi.backend.repository.community.CommunityPostRepository;
import com.otakuzi.backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostService {

    private final CommunityPostRepository communityPostRepository;
    private final CommunityCategoryRepository communityCategoryRepository;
    private final UserRepository userRepository;
}
