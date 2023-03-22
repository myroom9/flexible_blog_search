package com.whahn.type;

import lombok.Getter;

@Getter
public enum CacheType {
    TOP_TEN_KEYWORD_LIST(
            "topTenKeywordCountList", // 캐시 이름
            20, // 20초
            10 // 캐시 저장 최대 개수
    );

    private final String cacheName;
    private final int expireAfterWriter;
    private final int maximumSize;

    CacheType(String cacheName, int expireAfterWriter, int maximumSize) {
        this.cacheName = cacheName;
        this.expireAfterWriter = expireAfterWriter;
        this.maximumSize = maximumSize;
    }
}
