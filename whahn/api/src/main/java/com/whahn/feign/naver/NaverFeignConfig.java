package com.whahn.feign.naver;

import feign.Logger;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class NaverFeignConfig {

    @Bean
    public Request.Options options() {
        return new Request.Options(60000, TimeUnit.SECONDS,  60000, TimeUnit.SECONDS, true);
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Decoder feignDecoder() {
        return new NaverCustomFeignDecoder();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new NaverCustomErrorDecoder();
    }
}
