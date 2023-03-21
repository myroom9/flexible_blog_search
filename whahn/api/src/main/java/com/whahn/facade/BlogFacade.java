package com.whahn.facade;

import com.whahn.common.ErrorCode;
import com.whahn.controller.dto.BlogSearchPagingResponse;
import com.whahn.controller.dto.CustomRequestPaging;
import com.whahn.entity.ApiMetaInformation;
import com.whahn.entity.KeywordCount;
import com.whahn.exception.cumtom.BusinessException;
import com.whahn.exception.cumtom.FeignClientException;
import com.whahn.service.BlogService;
import com.whahn.service.KakaoBlogService;
import com.whahn.service.KeywordCountService;
import com.whahn.service.NaverBlogService;
import com.whahn.type.blog.CorporationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlogFacade {

    private final KakaoBlogService kakaoBlogService;
    private final NaverBlogService naverBlogService;
    private final KeywordCountService keywordCountService;

    /**
     * 블로그 검색하기
     */
    public BlogSearchPagingResponse getBlogContents(CustomRequestPaging request) {
        KeywordCount keywordCount = keywordCountService.saveKeywordCountAndGet(request.getSearchKeyword());
        keywordCountService.addKeywordCount(keywordCount);

        var blogService = getBlogServiceByCorporationType(request.getCorporationType());
        ApiMetaInformation apiMetaInformation = blogService.getApiMetaInformation();

        BlogSearchPagingResponse blogContents;
        try {
             blogContents = blogService.getBlogContents(request, apiMetaInformation);
        } catch (FeignClientException e) {
            // 위의 블로그 검색 실패시 무조건 네이버 API로 요청
            log.error("블로그 검색 API 통신 장애 발생", e);
            apiMetaInformation = naverBlogService.getApiMetaInformation();
            blogContents = naverBlogService.getBlogContents(request, apiMetaInformation);
        }

        List<KeywordCount> topTenKeywordList = keywordCountService.getKeywordCountList();
        return BlogSearchPagingResponse.addTopTenKeywordsToBlogResponse(blogContents, topTenKeywordList);
    }

    /**
     * 요청 값으로 조회할 블로그 서비스 객체 추출
     */
    private BlogService getBlogServiceByCorporationType(CorporationType corporationType) {
        if (CorporationType.KAKAO.equals(corporationType)) {
            return kakaoBlogService;
        }

        throw new BusinessException(ErrorCode.NOT_SUPPORT_SERVICE_CORPORATION);

    }

}
