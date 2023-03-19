package com.whahn.repository;

import com.whahn.common.ErrorCode;
import com.whahn.entity.KeywordCount;
import com.whahn.exception.cumtom.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@EnableJpaAuditing
@DataJpaTest
class KeywordCountRepositoryTest {

    @Autowired
    private KeywordCountRepository keywordCountRepository;

    @Test
    @DisplayName("[성공] keywordCount 저장하기")
    void saveKeywordCountSuccessTest() {
        KeywordCount testKeyword = KeywordCount.builder().count(0L).keyword("testKeyword").build();
        KeywordCount keywordCount = keywordCountRepository.save(testKeyword);

        Assertions.assertThat(keywordCount.getCount()).isEqualTo(testKeyword.getCount());
        Assertions.assertThat(keywordCount.getKeyword()).isEqualTo(testKeyword.getKeyword());
    }

    @Test
    @DisplayName("[예외] keywordCount 저장하기")
    void saveKeywordCountExceptionTest() {
        KeywordCount testKeyword = KeywordCount.builder().count(0L).build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            keywordCountRepository.save(testKeyword);
        });
    }

    @Test
    @DisplayName("[성공] 키워드로 keywordCount 객체 조회하기")
    void findKeywordCountByKeyword() {
        KeywordCount emptyKeywordCount = keywordCountRepository.findKeywordCountByKeyword("test")
                .orElse(null);

        KeywordCount testKeyword = KeywordCount.builder().count(0L).keyword("testKeyword").build();
        keywordCountRepository.save(testKeyword);

        KeywordCount keywordCount = keywordCountRepository.findKeywordCountByKeyword("testKeyword")
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_EXCEPTION));

        Assertions.assertThat(emptyKeywordCount).isNull();
        Assertions.assertThat(keywordCount.getKeyword()).isEqualTo(testKeyword.getKeyword());
        Assertions.assertThat(keywordCount.getCount()).isEqualTo(testKeyword.getCount());
    }

    @Test
    @DisplayName("[성공] 10개 상위 키워드 검색하기 (null or 1개)")
    void findTopTenKeywordByCountSuccessTest() {
        List<KeywordCount> emptyTopTenList = keywordCountRepository.findTopTenKeywordByCount();

        KeywordCount testKeyword = KeywordCount.builder().count(0L).keyword("testKeyword").build();
        keywordCountRepository.save(testKeyword);

        List<KeywordCount> topOneList = keywordCountRepository.findTopTenKeywordByCount();

        Assertions.assertThat(emptyTopTenList).isEmpty();
        Assertions.assertThat(topOneList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("[성공] 10개 상위 키워드 검색하기 (10개 이상인 경우)")
    void findTopTenKeywordByCountSuccessTest2() {
        List<KeywordCount> dummyKeywordCountList = IntStream.range(0, 15)
                .mapToObj(o -> KeywordCount.builder().count((long) o).keyword("testKeyword" + o).build())
                .toList();

        keywordCountRepository.saveAll(dummyKeywordCountList);

        List<KeywordCount> topTenList = keywordCountRepository.findTopTenKeywordByCount();

        Assertions.assertThat(topTenList.size()).isEqualTo(10);
        Assertions.assertThat(topTenList.get(0).getKeyword()).isEqualTo("testKeyword14");
    }

    @Test
    @DisplayName("[성공] 10개 상위 키워드 검색하기 (count가 같을 때 최근시간순으로 추출되는지)")
    void findTopTenKeywordByCountSuccessTest3() {
        List<KeywordCount> dummyKeywordCountList = IntStream.range(0, 15)
                .mapToObj(o -> KeywordCount.builder().count(1L).keyword("testKeyword" + o).build())
                .toList();

        keywordCountRepository.saveAll(dummyKeywordCountList);

        List<KeywordCount> topTenList = keywordCountRepository.findTopTenKeywordByCount();

        Assertions.assertThat(topTenList.size()).isEqualTo(10);
        Assertions.assertThat(topTenList.get(0).getKeyword()).isEqualTo("testKeyword14");
    }

}