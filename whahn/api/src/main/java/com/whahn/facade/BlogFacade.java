package com.whahn.facade;

import com.whahn.common.ErrorCode;
import com.whahn.controller.dto.BlogSearchPagingResponse;
import com.whahn.controller.dto.CustomRequestPaging;
import com.whahn.entity.ApiMetaInformation;
import com.whahn.entity.KeywordCount;
import com.whahn.exception.cumtom.BusinessException;
import com.whahn.service.BlogService;
import com.whahn.service.KakaoBlogService;
import com.whahn.service.KeywordCountService;
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
    private final KeywordCountService keywordCountService;

    /**
     * 블로그 검색하기
     */
    public BlogSearchPagingResponse getBlogContents(CustomRequestPaging request) {
        // 검색어 저장 (h2) - 이건 그냥 메소드단위로 처리.. / 큐처리하면 누락되는건 없음
        // TODO: 키워드 횟수 추가시 에러 발생시 검색은 되도록 구현 필요함
        KeywordCount keywordCount = keywordCountService.saveKeywordCountAndGet(request.getSearchKeyword());
        keywordCountService.addKeywordCount(keywordCount);

        var blogService = getBlogServiceByCorporationType(request.getCorporationType());
        // TODO: 시간 매핑 필요함
        ApiMetaInformation apiMetaInformation = blogService.getApiMetaInformation();
        BlogSearchPagingResponse blogContents = blogService.getBlogContents(request, apiMetaInformation);

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
