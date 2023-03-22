package com.whahn.service;

import com.whahn.controller.dto.BlogSearchPagingResponse;
import com.whahn.controller.dto.CustomRequestPaging;
import com.whahn.entity.ApiMetaInformation;
import com.whahn.feign.naver.NaverFeignClient;
import com.whahn.feign.dto.NaverBlogContent;
import com.whahn.type.blog.CorporationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 네이버 블로그 검색 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NaverBlogService implements BlogService {

    private final ApiMetaInformationService apiMetaInformationService;

    private final NaverFeignClient naverFeignClient;

    /**
     * 네이버 API 메타 정보 가져오기
     */
    @Override
    public ApiMetaInformation getApiMetaInformation() {
        return apiMetaInformationService.getApiMetaInformationByCorporationName(CorporationType.NAVER.getCorporationName());
    }

    /**
     * 네이버 blog API 정보 가져오기
     */
    @Override
    public BlogSearchPagingResponse getBlogContents(CustomRequestPaging request, ApiMetaInformation apiMetaInformation) {
        NaverBlogContent blogContents =
                naverFeignClient.getBlogContents(
                        apiMetaInformation.getApiKey(),
                        apiMetaInformation.getExtraKey(),
                        request.getSearchKeyword(),
                        request.getSortType().getNaverSortValue(),
                        request.getPage(), request.getSize());
        log.info("네이버 블로그 검색 결과: {}", blogContents);
        return BlogSearchPagingResponse.naverBlogResultToEntity(request, blogContents);
    }

}
