package com.whahn.facade;

import com.whahn.common.ErrorCode;
import com.whahn.controller.dto.BlogSearchPagingResponse;
import com.whahn.controller.dto.CustomRequestPaging;
import com.whahn.entity.ApiMetaInformation;
import com.whahn.exception.cumtom.BusinessException;
import com.whahn.service.BlogService;
import com.whahn.service.KakaoBlogService;
import com.whahn.type.blog.CorporationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlogFacade {

    private final KakaoBlogService kakaoBlogService;

    /**
     * 블로그 검색하기
     */
    public BlogSearchPagingResponse getBlogContents(CustomRequestPaging request) {
        BlogService blogService = getBlogServiceByCorporationType(request.getCorporationType());

        ApiMetaInformation apiMetaInformation = blogService.getMetaInformation();

        return blogService.getBlogContents(request, apiMetaInformation);
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
