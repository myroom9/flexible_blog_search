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
class KakaoBlogServiceTest {
    @Autowired
    private KakaoBlogService kakaoBlogService;

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
    @DisplayName("[성공] 카카오 API 메타정보 가져오기")
    void getApiMetaInformationSuccessTest() {
        ApiMetaInformation apiMetaInformation = kakaoBlogService.getApiMetaInformation();

        Assertions.assertThat(apiMetaInformation.getApiKey()).isEqualTo("01975332e2b1c7d87aaaf0efa345bb52");
        Assertions.assertThat(apiMetaInformation.getCorporationName()).isEqualTo(CorporationType.KAKAO.getCorporationName());
    }

    @Test
    @DisplayName("[성공] 카카오 블로그 검색 API 요청")
    void getBlogContentsSuccessTest() {
        ApiMetaInformation apiMetaInformation = kakaoBlogService.getApiMetaInformation();
        CustomRequestPaging mockCustomRequestPaging = getMockCustomRequestPaging();
        BlogSearchPagingResponse blogContents = kakaoBlogService.getBlogContents(mockCustomRequestPaging, apiMetaInformation);

        Assertions.assertThat(blogContents.getMeta()).isNotNull();
        Assertions.assertThat(blogContents.getDocument().size()).isEqualTo(10);
    }

    @Test
    @DisplayName("[예외] 카카오 블로그 검색 API 요청 (apiKey 실패)")
    void getBlogContentsExceptionTest() {
        ApiMetaInformation apiMetaInformation = ApiMetaInformation.builder().apiKey("test").corporationName(CorporationType.KAKAO.getCorporationName()).build();
        CustomRequestPaging mockCustomRequestPaging = getMockCustomRequestPaging();

        FeignClientException exception = assertThrows(FeignClientException.class, () -> {
            kakaoBlogService.getBlogContents(mockCustomRequestPaging, apiMetaInformation);
        });

        Assertions.assertThat(exception.getLocalizedMessage()).isEqualTo("API 통신 에러 발생 사유: wrong appKey(test) format");
    }

    @Test
    @DisplayName("[예외] 카카오 블로그 검색 API 요청 (max page 이상 요청)")
    void getBlogContentsExceptionTest2() {
        ApiMetaInformation apiMetaInformation = ApiMetaInformation.builder().apiKey("01975332e2b1c7d87aaaf0efa345bb52").corporationName(CorporationType.KAKAO.getCorporationName()).build();
        CustomRequestPaging mockCustomRequestPaging = getMockCustomRequestPaging();
        mockCustomRequestPaging.setPage(51);

        FeignClientException exception = assertThrows(FeignClientException.class, () -> {
            kakaoBlogService.getBlogContents(mockCustomRequestPaging, apiMetaInformation);
        });

        Assertions.assertThat(exception.getLocalizedMessage()).isEqualTo("API 통신 에러 발생 사유: page is more than max");
    }

}