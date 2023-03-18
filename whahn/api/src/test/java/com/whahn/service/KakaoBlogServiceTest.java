package com.whahn.service;

import com.whahn.controller.dto.BlogSearchPagingResponse;
import com.whahn.controller.dto.CustomRequestPaging;
import com.whahn.feign.KakaoFeignClient;
import com.whahn.type.blog.SortType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KakaoBlogServiceTest {
    @Autowired
    KakaoFeignClient kakaoFeignClient;

    @Autowired
    KakaoBlogService kakaoBlogService;

}