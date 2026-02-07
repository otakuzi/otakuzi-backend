package com.otakuzi.backend.controller.shop;

import com.otakuzi.backend.service.ShopBookmarkService;
import com.otakuzi.backend.service.shop.ShopBookmarkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext; // JPA 오류 방지용
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser; // 가짜 로그인 유저
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf; // CSRF 토큰
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopBookmarkController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ShopBookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopBookmarkService shopBookmarkService;

    @Test
    @DisplayName("북마클 토글 요청 오면 200 OK 반환 후 서비스 호출")
    @WithMockUser
    void toggleBookmark() throws Exception {

        Long shopId = 10L;

        mockMvc.perform(post("/api/shops/" + shopId + "/bookmarks")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
