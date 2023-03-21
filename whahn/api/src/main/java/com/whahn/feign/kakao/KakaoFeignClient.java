package com.whahn.feign.kakao;

import com.whahn.feign.dto.KakaoBlogContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="kakaoBlog", url = "${feign.request-url.kakao}", configuration = KakaoFeignConfig.class)
public interface KakaoFeignClient {
    @GetMapping(value = "/v2/search/blog", consumes = MediaType.APPLICATION_JSON_VALUE)
    KakaoBlogContent getBlogContents(@RequestHeader("Authorization") String apiKey,
                                     @RequestParam("query") String searchKeyword,
                                     @RequestParam("sort") String sortType,
                                     @RequestParam("page") int page, @RequestParam("size") int size);

}
