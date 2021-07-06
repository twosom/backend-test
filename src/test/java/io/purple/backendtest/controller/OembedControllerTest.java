package io.purple.backendtest.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OembedControllerTest {

    public static final String TEST_YOUTUBE_LINK = "https://www.youtube.com/watch?v=dBD54EZIrZo";
    public static final String NOT_URL = "not_url";
    @Autowired
    MockMvc mockMvc;

    @DisplayName("유튜브 링크 입력 - 성공")
    @Test
    void oembed_with_youtube_success() throws Exception {
        mockMvc.perform(get("/search")
                .param("url", TEST_YOUTUBE_LINK))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("result", "searchForm"))
                .andExpect(view().name("index"));
    }

    @DisplayName("유튜브 링크 입력 - 실패")
    @Test
    void oembed_with_youtube_failed() throws Exception {

        mockMvc.perform(get("/search")
                .param("url", NOT_URL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error", "searchForm"))
                .andExpect(view().name("index"));
    }


}