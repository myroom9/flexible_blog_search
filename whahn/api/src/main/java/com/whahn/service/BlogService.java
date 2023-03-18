package com.whahn.service;

import com.whahn.controller.dto.BlogSearchPagingResponse;
import com.whahn.controller.dto.CustomRequestPaging;
import com.whahn.entity.ApiMetaInformation;

/**
 * 블로그 서비스 인터페이스
 */
public interface BlogService {
    // api 메타정보 조회
    ApiMetaInformation getMetaInformation();

    // 블로그 정보 조회 요청
    BlogSearchPagingResponse getBlogContents(CustomRequestPaging request, ApiMetaInformation apiMetaInformation);
}
