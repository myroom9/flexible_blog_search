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
    @Parameter
    private Integer page = 1;

    @Schema(description = "조회 페이지 크기", defaultValue = "10")
    @Parameter
    private Integer size = 10;

    @Schema(description = "조회 정렬 조건 (accuracy, currency)", defaultValue = "accuracy")
    @Parameter
    private SortType sortType = SortType.ACCURACY;

    @Schema(description = "조회 blog 회사 (KAKAO, NAVER)", defaultValue = "KAKAO")
    @Parameter
    private CorporationType corporationType = CorporationType.KAKAO;

    @Schema(description = "조회 키워드")
    @Parameter(required = true)
    @NotBlank
    private String searchKeyword;
}
