package com.whahn.service;

import com.whahn.controller.dto.BlogSearchPagingResponse;
import com.whahn.controller.dto.CustomRequestPaging;
import com.whahn.entity.ApiMetaInformation;
import com.whahn.exception.cumtom.FeignClientException;
import com.whahn.type.blog.CorporationType;
import com.whahn.type.blog.SortType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NaverBlogServiceTest {
    @Autowired
    private NaverBlogService naverBlogService;

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
    @DisplayName("[성공] 네이버 API 메타정보 가져오기")
    void getApiMetaInformationSuccessTest() {
        ApiMetaInformation apiMetaInformation = naverBlogService.getApiMetaInformation();

        Assertions.assertThat(apiMetaInformation.getApiKey()).isEqualTo("bJjcQ2JZc1o5hbxzw4k2");
        Assertions.assertThat(apiMetaInformation.getExtraKey()).isEqualTo("P7rfSPkmEi");
        Assertions.assertThat(apiMetaInformation.getCorporationName()).isEqualTo(CorporationType.NAVER.getCorporationName());
    }

    @Test
    @DisplayName("[성공] 네이 블로그 검색 API 요청")
    void getBlogContentsSuccessTest() {
        ApiMetaInformation apiMetaInformation = naverBlogService.getApiMetaInformation();
        CustomRequestPaging mockCustomRequestPaging = getMockCustomRequestPaging();
        BlogSearchPagingResponse blogContents = naverBlogService.getBlogContents(mockCustomRequestPaging, apiMetaInformation);

        Assertions.assertThat(blogContents.getMeta()).isNotNull();
        Assertions.assertThat(blogContents.getDocument().size()).isEqualTo(10);
    }

    @Test
    @DisplayName("[예외] 네이버 블로그 검색 API 요청 (apiKey 실패)")
    void getBlogContentsExceptionTest() {
        ApiMetaInformation apiMetaInformation = ApiMetaInformation.builder().apiKey("test").corporationName(CorporationType.NAVER.getCorporationName()).build();
        CustomRequestPaging mockCustomRequestPaging = getMockCustomRequestPaging();

        FeignClientException exception = assertThrows(FeignClientException.class, () -> {
            naverBlogService.getBlogContents(mockCustomRequestPaging, apiMetaInformation);
        });

        Assertions.assertThat(exception.getLocalizedMessage()).isEqualTo("API 통신 에러 발생 사유: Not Exist Client Secret : Authentication failed. (인증에 실패했습니다.)");
    }

    @Test
    @DisplayName("[예외] 네이버 블로그 검색 API 요청 (size max over 실패)")
    void getBlogContentsExceptionTest2() {
        ApiMetaInformation apiMetaInformation = ApiMetaInformation.builder()
                .apiKey("bJjcQ2JZc1o5hbxzw4k2")
                .extraKey("P7rfSPkmEi")
                .corporationName(CorporationType.NAVER.getCorporationName()).build();
        CustomRequestPaging mockCustomRequestPaging = getMockCustomRequestPaging();
        mockCustomRequestPaging.setSize(101);

        FeignClientException exception = assertThrows(FeignClientException.class, () -> {
            naverBlogService.getBlogContents(mockCustomRequestPaging, apiMetaInformation);
        });

        Assertions.assertThat(exception.getLocalizedMessage()).isEqualTo("API 통신 에러 발생 사유: Invalid display value (부적절한 display 값입니다.)");
    }
}