package com.whahn.feign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiExceptionResponse {
    private int status;
    private String message;
    private String errorType;
    private String path;
    private String timestamp;
}
