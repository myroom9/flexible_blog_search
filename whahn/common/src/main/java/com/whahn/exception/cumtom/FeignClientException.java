package com.whahn.exception.cumtom;

import com.whahn.common.ErrorCode;
import lombok.Getter;

/**
 * feign 400 ~ 499 error
 * client error
 */
public class FeignClientException extends RuntimeException {
    @Getter
    private final ErrorCode errorCode;

    public FeignClientException(ErrorCode errorCode, String errorMessage) {
        super(String.format(errorCode.getMessage(), errorMessage));
        this.errorCode = errorCode;
    }

    public FeignClientException(ErrorCode errorCode, Throwable e, String errorMessage) {
        super(String.format(errorCode.getMessage(), errorMessage), e);
        this.errorCode = errorCode;
    }
}
