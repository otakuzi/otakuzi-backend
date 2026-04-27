package com.otakuzi.backend.dto.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCreateRequest {

    private Integer categoryId;

    private String title;

    private String content;
}
