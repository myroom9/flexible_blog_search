package com.whahn.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whahn.common.ErrorCode;
import com.whahn.exception.cumtom.FeignClientException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

/**
 * feign client custom error decoder 생성
 */
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        ApiExceptionResponse apiExceptionResponse = null;
        try (InputStream body = response.body().asInputStream()) {
            apiExceptionResponse = new ObjectMapper().readValue(body, ApiExceptionResponse.class);
        } catch (IOException e) {
            return new FeignClientException(ErrorCode.FEIGN_CLIENT_ERROR, e, e.getMessage());
        }

        return new FeignClientException(ErrorCode.FEIGN_CLIENT_ERROR, apiExceptionResponse.getMessage());
    }
}
