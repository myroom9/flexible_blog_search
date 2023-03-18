package com.whahn.type.blog;

import lombok.Getter;

/**
 * 블로그 조회 정렬 타입
 */
@Getter
public enum SortType {

    ACCURACY("accuracy", "정확도 기준"),
    RECENCY("recency", "최신 생성 기준"),
    ;

    private final String value;
    private final String description;

    SortType(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
