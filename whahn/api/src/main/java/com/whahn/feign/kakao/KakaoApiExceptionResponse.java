package com.whahn.feign.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoApiExceptionResponse {
    private int status;
    private String message;
    private String errorType;
    private String path;
    private String timestamp;
}
