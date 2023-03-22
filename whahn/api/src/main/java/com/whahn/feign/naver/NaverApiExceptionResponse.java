package com.whahn.feign.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverApiExceptionResponse {
    private int status;
    private String errorMessage;
    private String errorCode;
}
