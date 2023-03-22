package com.whahn.type.blog;

import lombok.Getter;

/**
 * 블로그 조회 정렬 타입
 */
@Getter
public enum SortType {

    ACCURACY("accuracy", "sim", "정확도 기준"),
    RECENCY("recency", "date", "최신 생성 기준"),
    ;

    private final String kakaoSortValue;
    private final String naverSortValue;
    private final String description;

    SortType(String kakaoSortValue, String naverSortValue, String description) {
        this.kakaoSortValue = kakaoSortValue;
        this.naverSortValue = naverSortValue;
        this.description = description;
    }
}
