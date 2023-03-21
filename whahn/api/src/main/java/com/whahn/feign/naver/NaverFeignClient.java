package com.whahn.feign.naver;

import com.whahn.feign.dto.NaverBlogContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="naverBlog", url = "${feign.request-url.naver}", configuration = NaverFeignConfig.class)
public interface NaverFeignClient {
    @GetMapping(value = "/v1/search/blog.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    NaverBlogContent getBlogContents(@RequestHeader("X-Naver-Client-Id") String clientId,
                                     @RequestHeader("X-Naver-Client-Secret") String clientSecretKey,
                                     @RequestParam("query") String searchKeyword,
                                     @RequestParam("sort") String sortType,
                                     @RequestParam("start") int page,
                                     @RequestParam("display") int size);

}
