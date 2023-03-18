package com.whahn.type.blog;

import lombok.Getter;

/**
 * blog 조회 API 회사들
 */
@Getter
public enum CorporationType {

    KAKAO("kakao", "카카오API"),
    NAVER("naver", "네이버API"),
    ;

    private final String corporationName;
    private final String description;

    CorporationType(String corporationName, String description) {
        this.corporationName = corporationName;
        this.description = description;
    }
}
