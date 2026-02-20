package com.otakuzi.backend.controller.shop;

import com.otakuzi.backend.global.annotation.WithCustomMockUser;
import com.otakuzi.backend.global.base.BaseControllerTest;
import com.otakuzi.backend.service.shop.ShopBookmarkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopBookmarkController.class)
class ShopBookmarkControllerTest extends BaseControllerTest {

    @MockitoBean
    private ShopBookmarkService shopBookmarkService;

    @Test
    @DisplayName("북마클 토글 요청 오면 200 OK 반환 후 서비스 호출 - 커스텀 어노테이션 사용")
    @WithCustomMockUser
    void toggleBookmark() throws Exception {

        Long shopId = 10L;

        mockMvc.perform(post("/api/shops/" + shopId + "/bookmarks")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
