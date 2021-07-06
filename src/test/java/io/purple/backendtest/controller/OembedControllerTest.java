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

    private static final String TEST_YOUTUBE_LINK = "https://www.youtube.com/watch?v=dBD54EZIrZo";
    private static final String TEST_INSTAGRAM_LINK = "https://www.instagram.com/p/BUawPlPF_Rx/";
    private static final String TEST_TWITTER_LINK = "https://twitter.com/hellopolicy/status/867177144815804416";
    private static final String NOT_URL = "not_url";



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

    @DisplayName("링크 입력 - 실패")
    @Test
    void oembed_with_youtube_failed() throws Exception {

        mockMvc.perform(get("/search")
                .param("url", NOT_URL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error", "searchForm"))
                .andExpect(view().name("index"));
    }

    @DisplayName("인스타그램 링크 입력 - 성공")
    @Test
    void oembed_with_instagram_success() throws Exception {
        mockMvc.perform(get("/search")
                .param("url", TEST_INSTAGRAM_LINK))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("result", "searchForm"))
                .andExpect(view().name("index"));
    }

    @DisplayName("트위터 링크 입력 - 성공")
    @Test
    void oembed_with_twitter_success() throws Exception {
        mockMvc.perform(get("/search")
                .param("url", TEST_TWITTER_LINK))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("result", "searchForm"))
                .andExpect(view().name("index"));
    }



}