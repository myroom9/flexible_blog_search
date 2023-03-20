package com.whahn.config;

import com.whahn.feign.CustomErrorDecoder;
import feign.Logger;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableFeignClients(basePackages = "com.whahn.feign")
@Configuration(proxyBeanMethods = false)
public class FeignConfig {

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
        return new JacksonDecoder();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
