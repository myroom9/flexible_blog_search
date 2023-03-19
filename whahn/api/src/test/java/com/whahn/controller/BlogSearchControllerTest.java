package com.whahn.controller;

import com.whahn.controller.dto.BlogSearchPagingResponse;
import com.whahn.controller.dto.CustomRequestPaging;
import com.whahn.entity.KeywordCount;
import com.whahn.facade.BlogFacade;
import com.whahn.type.blog.CorporationType;
import com.whahn.type.blog.SortType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static com.whahn.controller.dto.BlogSearchPagingResponse.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogSearchController.class)
class BlogSearchControllerTest {

    @MockBean
    private BlogFacade blogFacade;

    @Autowired
    private MockMvc mockMvc;

    private BlogSearchPagingResponse getMockBlogSearchPagingResponse() {
        Meta meta = Meta.builder()
                .currentPage(1)
                .size(10)
                .totalContentCount(1000)
                .isEnd(false).build();

        Document document = Document.builder()
                .blogname("blogname")
                .url("url")
                .title("title")
                .contents("content")
                .build();

        return builder()
                .meta(meta)
                .document(Collections.singletonList(document))
                .topTenKeywords(getTopTenKeyword())
                .build();
    }

    private List<TopTenKeyword> getTopTenKeyword() {
        return IntStream.range(0, 10)
                .mapToObj(o -> KeywordCount.builder().count(1L).keyword("testKeyword" + o).build())
                .toList()
                .stream()
                .map(TopTenKeyword::new)
                .toList();
    }

    private CustomRequestPaging getMockCustomRequestPaging() {
        CustomRequestPaging customRequestPaging = new CustomRequestPaging();
        customRequestPaging.setPage(1);
        customRequestPaging.setSize(10);
        customRequestPaging.setSortType(SortType.ACCURACY);
        customRequestPaging.setCorporationType(CorporationType.KAKAO);
        customRequestPaging.setSearchKeyword("테스트");
        return customRequestPaging;
    }

    @Test
    @DisplayName("[성공] 블로그 조회")
    void searchBlogSuccessTest() throws Exception {

        given(blogFacade.getBlogContents(getMockCustomRequestPaging()))
                .willReturn(getMockBlogSearchPagingResponse());

        mockMvc.perform(get("/v1/blog")
                .param("page", "1")
                .param("size", "10")
                .param("sortType", SortType.ACCURACY.getValue())
                .param("corporationType", CorporationType.KAKAO.toString())
                .param("searchKeyword", "테스트"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.code").value("20000000"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.topTenKeywords").isArray())
                .andExpect(jsonPath("$.data.document").isArray())
                .andExpect(jsonPath("$.data.meta").exists())
        ;
    }

}