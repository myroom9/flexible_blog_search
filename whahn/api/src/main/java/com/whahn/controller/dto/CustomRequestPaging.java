package com.whahn.controller.dto;

import com.whahn.type.blog.CorporationType;
import com.whahn.type.blog.SortType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;

@Data
@ParameterObject
public class CustomRequestPaging {
    @Schema(description = "조회 요청 페이지", defaultValue = "1")
    @Parameter(description = "조회 요청 페이지")
    private Integer page = 1;

    @Schema(description = "조회 페이지 크기", defaultValue = "10")
    @Parameter(description = "조회 페이지 크기 (51입력시 카카오API error로 자동으로 네이버 API연동됨)")
    private Integer size = 10;

    @Schema(description = "조회 정렬 조건 (accuracy, currency)", defaultValue = "accuracy")
    @Parameter(description = "조회 정렬 조건 (accuracy, currency)")
    private SortType sortType = SortType.ACCURACY;

    @Schema(description = "조회 blog 회사 (KAKAO, NAVER)", defaultValue = "KAKAO")
    @Parameter(description = "조회 blog 회사 (KAKAO, NAVER)")
    private CorporationType corporationType = CorporationType.KAKAO;

    @Schema(description = "조회 키워드")
    @Parameter(required = true, description = "조회 키워드")
    @NotBlank
    private String searchKeyword;
}
