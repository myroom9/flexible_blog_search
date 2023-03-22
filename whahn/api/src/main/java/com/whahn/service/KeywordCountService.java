package com.whahn.service;

import com.whahn.entity.KeywordCount;
import com.whahn.repository.KeywordCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordCountService {
    private final KeywordCountRepository keywordCountRepository;

    /**
     * 저장된 키워드가 없다면 저장 / 있다면 저장된 값 리턴
     */
    public synchronized KeywordCount saveKeywordCountAndGet(String keyword) {
        return keywordCountRepository.findKeywordCountByKeyword(keyword)
                .orElseGet(() -> {
                    KeywordCount newKeywordCount = createKeywordCount(keyword);
                    return keywordCountRepository.save(newKeywordCount);
                });
    }

    /**
     * 키워드 검색 횟수 증가
     */
    @Transactional
    public synchronized void addKeywordCount(KeywordCount keywordCount) {
        keywordCount.addCount(keywordCount);
    }

    @Cacheable(cacheNames = "topTenKeywordCountList", unless = "#result.size <= 9")
    @Transactional(readOnly = true)
    public List<KeywordCount> getKeywordCountList() {
        List<KeywordCount> topTenKeywordByCount = keywordCountRepository.findTopTenKeywordByCount();
        log.info("캐시 안타고 리스트 조회 결과: {}", topTenKeywordByCount);
        return topTenKeywordByCount;
    }

    /**
     * 키워드 객체 생성
     */
    private static KeywordCount createKeywordCount(String keyword) {
        return KeywordCount.builder().keyword(keyword).count(0L).build();
    }
}
