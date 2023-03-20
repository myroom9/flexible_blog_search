package com.whahn.feign;

import com.whahn.config.FeignConfig;
import com.whahn.feign.dto.KakaoBlogContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="kakaoBlog", url = "${feign.request-url.kakao}", configuration = FeignConfig.class)
public interface KakaoFeignClient {
    @GetMapping(value = "/v2/search/blog", consumes = MediaType.APPLICATION_JSON_VALUE)
    KakaoBlogContent getBlogContents(@RequestHeader("Authorization") String apiKey,
                                     @RequestParam("query") String searchKeyword,
                                     @RequestParam("sort") String sortType,
                                     @RequestParam("page") int page, @RequestParam("size") int size);

}
