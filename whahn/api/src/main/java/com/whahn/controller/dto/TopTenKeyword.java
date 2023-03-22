package com.whahn.controller.dto;

import com.whahn.entity.KeywordCount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "인기 검색어 탑 10 응답 객체")
@Data
public class TopTenKeyword {
    @Schema(description = "인기 검색어")
    private String keyword;
    @Schema(description = "인기 검색어 검색 횟수")
    private long count;

    public TopTenKeyword(KeywordCount keywordCount) {
        this.keyword = keywordCount.getKeyword();
        this.count = keywordCount.getCount();
    }
}
