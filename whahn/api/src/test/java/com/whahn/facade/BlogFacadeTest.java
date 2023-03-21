package com.whahn.facade;

import com.whahn.controller.dto.BlogSearchPagingResponse;
import com.whahn.controller.dto.CustomRequestPaging;
import com.whahn.entity.KeywordCount;
import com.whahn.exception.cumtom.FeignClientException;
import com.whahn.service.KakaoBlogService;
import com.whahn.service.KeywordCountService;
import com.whahn.service.NaverBlogService;
import com.whahn.type.blog.CorporationType;
import com.whahn.type.blog.SortType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BlogFacadeTest {

    @Mock
    private KakaoBlogService kakaoBlogService;
    @Mock
    private NaverBlogService naverBlogService;
    @Mock
    private KeywordCountService keywordCountService;
    @InjectMocks
    private BlogFacade blogFacade;

    private BlogSearchPagingResponse getBlogSearchPagingResponse() {
        BlogSearchPagingResponse.Meta meta = BlogSearchPagingResponse.Meta.builder()
                .currentPage(1)
                .size(10)
                .totalContentCount(1000)
                .isEnd(false).build();

        BlogSearchPagingResponse.Document document = BlogSearchPagingResponse.Document.builder()
                .blogname("blogname")
                .url("url")
                .title("title")
                .contents("content")
                .build();

        return BlogSearchPagingResponse.builder()
                .meta(meta)
                .document(Collections.singletonList(document))
                .build();
    }

    private List<KeywordCount> getKeywordCountList() {
        return IntStream.range(0, 10)
                .mapToObj(o -> KeywordCount.builder().count(1L).keyword("testKeyword" + o).build())
                .toList();
    }

    private CustomRequestPaging getMockRequest() {
        CustomRequestPaging customRequestPaging = new CustomRequestPaging();
        customRequestPaging.setCorporationType(CorporationType.KAKAO);
        customRequestPaging.setPage(1);
        customRequestPaging.setSize(10);
        customRequestPaging.setSearchKeyword("test");
        customRequestPaging.setSortType(SortType.ACCURACY);
        return customRequestPaging;
    }

    @Test
    @DisplayName("[성공] 블로그 검색 API FACADE 테스트")
    void getBlogContentsSucessTest() {
        Mockito.when(kakaoBlogService.getBlogContents(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(getBlogSearchPagingResponse());

        Mockito.when(keywordCountService.getKeywordCountList())
                .thenReturn(getKeywordCountList());

        BlogSearchPagingResponse blogContents = blogFacade.getBlogContents(getMockRequest());

        Assertions.assertThat(blogContents.getMeta()).isNotNull();
        Assertions.assertThat(blogContents.getDocument().size()).isEqualTo(1);
        Assertions.assertThat(blogContents.getTopTenKeywords().size()).isEqualTo(10);
    }

    @Test
    @DisplayName("[성공] 블로그 검색 API FACADE 테스트 (카카오 API 실패시)")
    void getBlogContentsSuccessTest2() {
        Mockito.when(kakaoBlogService.getBlogContents(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenThrow(FeignClientException.class);

        Mockito.when(naverBlogService.getBlogContents(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(getBlogSearchPagingResponse());

        Mockito.when(keywordCountService.getKeywordCountList())
                .thenReturn(getKeywordCountList());

        BlogSearchPagingResponse blogContents = blogFacade.getBlogContents(getMockRequest());

        Assertions.assertThat(blogContents.getMeta()).isNotNull();
        Assertions.assertThat(blogContents.getDocument().size()).isEqualTo(1);
        Assertions.assertThat(blogContents.getTopTenKeywords().size()).isEqualTo(10);
    }

}