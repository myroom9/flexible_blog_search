package com.whahn.service;

import com.whahn.controller.dto.BlogSearchPagingResponse;
import com.whahn.controller.dto.CustomRequestPaging;
import com.whahn.entity.ApiMetaInformation;
import com.whahn.feign.KakaoFeignClient;
import com.whahn.feign.dto.KakaoBlogContent;
import com.whahn.type.blog.CorporationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoBlogService implements BlogService {

    private final ApiMetaInformationService apiMetaInformationService;

    private final KakaoFeignClient kakaoFeignClient;

    /**
     * 카카오 API 메타 정보 가져오기
     */
    @Override
    public ApiMetaInformation getApiMetaInformation() {
        return apiMetaInformationService.getApiMetaInformationByCorporationName(CorporationType.KAKAO.getCorporationName());
    }

    /**
     * 카카오 blog API 정보 가져오기
     */
    @Override
    public BlogSearchPagingResponse getBlogContents(CustomRequestPaging request, ApiMetaInformation apiMetaInformation) {
        KakaoBlogContent blogContents =
                kakaoFeignClient.getBlogContents(
                        String.format("KakaoAK %s", apiMetaInformation.getApiKey()),
                        request.getSearchKeyword(),
                        request.getSortType().getValue(),
                        request.getPage(),
                        request.getSize());
        log.info("카카오 블로그 검색 결과: {}", blogContents);
        return BlogSearchPagingResponse.kakaoBlogResultToEntity(request, blogContents);
    }

}
