package com.whahn.service;

import com.whahn.entity.KeywordCount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KeywordCountServiceTest {

    @Autowired
    private KeywordCountService keywordCountService;

    @Test
    void test() {
        List<KeywordCount> keywordCountList = keywordCountService.getKeywordCountList();
        System.out.println("keywordCountList = " + keywordCountList);

        KeywordCount keywordCount = keywordCountService.saveKeywordCountAndGet("test");
        keywordCount.addCount(keywordCount);

        keywordCountService.saveKeywordCountAndGet("test1");
        keywordCountService.saveKeywordCountAndGet("test2");
        keywordCountService.saveKeywordCountAndGet("test3");

        keywordCountService.getKeywordCountList();
        keywordCountService.getKeywordCountList();

    }

}