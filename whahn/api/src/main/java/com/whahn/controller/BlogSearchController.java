package com.whahn.controller;

import com.whahn.common.ApiResponse;
import com.whahn.controller.dto.BlogSearchPagingResponse;
import com.whahn.controller.dto.CustomRequestPaging;
import com.whahn.controller.dto.TopTenKeyword;
import com.whahn.entity.KeywordCount;
import com.whahn.facade.BlogFacade;
import com.whahn.service.KeywordCountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "블로그 조회 API", description = "블로그 조회 API")
public class BlogSearchController {

    private final BlogFacade blogFacade;
    private final KeywordCountService keywordCountService;

    @GetMapping("/v1/search/blog")
    @Operation(summary = "블로그 조회 (정확도, 최신수)")
    public ApiResponse<BlogSearchPagingResponse> searchBlog(@Valid CustomRequestPaging request) {
        BlogSearchPagingResponse response = blogFacade.getBlogContents(request);
        return ApiResponse.success(response);
    }

    @GetMapping("/v1/topten-keyword")
    @Operation(summary = "상위 10개 키워드 조회")
    public ApiResponse<List<TopTenKeyword>> getTopTenKeyword() {
        List<KeywordCount> topTenKeywordList = keywordCountService.getKeywordCountList();
        List<TopTenKeyword> data = topTenKeywordList.stream().map(TopTenKeyword::new).toList();
        return ApiResponse.success(data);
    }
}
