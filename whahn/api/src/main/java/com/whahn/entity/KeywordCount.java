package com.whahn.entity;

import com.whahn.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Table(name = "keyword_count", indexes = @Index(name = "idx__keyword", columnList = "keyword"))
@Where(clause = "deleted_at is null")
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeywordCount extends BaseEntity {

    @Builder
    public KeywordCount(String keyword, Long count) {
        this.keyword = keyword;
        this.count = count;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false, unique = true)
    private String keyword;

    @Column
    @ColumnDefault("0")
    private Long count;

    /**
     * 키워드 횟수 1회 증가
     */
    public void addCount(KeywordCount keywordCount) {
        keywordCount.count++;
    }
}
