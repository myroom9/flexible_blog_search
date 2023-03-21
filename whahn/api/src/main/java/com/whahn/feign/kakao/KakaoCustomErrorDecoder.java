package com.whahn.feign.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whahn.common.ErrorCode;
import com.whahn.exception.cumtom.FeignClientException;
import com.whahn.feign.kakao.KakaoApiExceptionResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * feign client custom error decoder 생성
 */
public class KakaoCustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        KakaoApiExceptionResponse apiExceptionResponse = null;
        try (InputStream body = response.body().asInputStream()) {
            apiExceptionResponse = new ObjectMapper().readValue(body, KakaoApiExceptionResponse.class);
        } catch (IOException e) {
            return new FeignClientException(ErrorCode.FEIGN_CLIENT_ERROR, e, e.getMessage());
        }

        if (ObjectUtils.isEmpty(apiExceptionResponse.getMessage())) {
            apiExceptionResponse.setMessage("API 통신에 실패하였습니다.");
        }

        return new FeignClientException(ErrorCode.FEIGN_CLIENT_ERROR, apiExceptionResponse.getMessage());
    }
}
