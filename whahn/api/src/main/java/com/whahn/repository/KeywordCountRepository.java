package com.whahn.repository;

import com.whahn.entity.KeywordCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KeywordCountRepository extends JpaRepository<KeywordCount, Long> {

    // 키워드 횟수 조회 by 키워드
    Optional<KeywordCount> findKeywordCountByKeyword(String keyword);

    // 검색 횟수 상위 10개 키워드 가져오기
    @Query("SELECT k FROM KeywordCount k WHERE k.deletedAt IS NULL ORDER BY k.count DESC, k.updatedAt DESC LIMIT 10")
    List<KeywordCount> findTopTenKeywordByCount();
}
